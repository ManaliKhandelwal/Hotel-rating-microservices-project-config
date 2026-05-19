package com.example.user.service.service.impl;

import com.example.user.service.entity.Hotel;
import com.example.user.service.entity.Rating;
import com.example.user.service.entity.Users;
import com.example.user.service.exception.ResourceNotFoundException;
import com.example.user.service.external.service.HotelService;
import com.example.user.service.external.service.RatingService;
import com.example.user.service.payload.ApiResponse;
import com.example.user.service.repositories.UserRepository;
import com.example.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final HotelService hotelService;
    private final RatingService ratingService;
    private final UserRepository userRepository;

    public UserServiceImpl(HotelService hotelService,
                           RatingService ratingService,
                           UserRepository userRepository) {
        this.hotelService = hotelService;
        this.ratingService = ratingService;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Users> getAllUser(int page, int size) {
        Page<Users> usersPage = userRepository.findAll(PageRequest.of(page, size));
        List<Users> users = usersPage.getContent();

        Set<String> userIds = users.stream()
                .map(Users::getUserId)
                .collect(Collectors.toSet());

        // ← Circuit breaker protects this call
        List<Rating> allRatings = fetchRatingsByUserIds(userIds);

        Map<String, List<Rating>> ratingsByUser = allRatings.stream()
                .collect(Collectors.groupingBy(Rating::getUserId));

        Set<String> hotelIds = allRatings.stream()
                .map(Rating::getHotelId)
                .collect(Collectors.toSet());

        // ← Circuit breaker protects this call
        List<Hotel> hotels = fetchHotelsByIds(hotelIds);
        Map<String, Hotel> hotelMap = hotels.stream()
                .collect(Collectors.toMap(Hotel::getId, h -> h));

        users.forEach(user -> {
            List<Rating> ratings = ratingsByUser.getOrDefault(user.getUserId(), new ArrayList<>());
            ratings.forEach(rating -> rating.setHotel(hotelMap.get(rating.getHotelId())));
            user.setRatings(ratings);
        });

        return new PageImpl<>(users, usersPage.getPageable(), usersPage.getTotalElements());
    }

    @Override
    public Users getUserById(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found with id :" + userId));

        // ← Circuit breaker protects this call
        List<Rating> ratingList = fetchRatingsForUser(userId);

        List<Rating> finalratingListWithHotel = ratingList.stream().map(rating -> {
            // ← Circuit breaker protects this call
            ResponseEntity<ApiResponse<Hotel>> hotel = fetchHotel(rating.getHotelId());

            if (hotel.getBody() != null && hotel.getBody().getData() != null) {
                Hotel hotelBody = hotel.getBody().getData();
                rating.setHotel(hotelBody);
            }
            return rating;
        }).toList();

        user.setRatings(finalratingListWithHotel);
        return user;
    }

    @CircuitBreaker(name = "ratingServiceCB", fallbackMethod = "fetchRatingsForUserFallback")
    public List<Rating> fetchRatingsForUser(String userId) {
        log.info("Fetching ratings for user: {}", userId);
        return ratingService.getRatings(userId);
    }

    public List<Rating> fetchRatingsForUserFallback(String userId, Exception ex) {
        log.warn("RatingService DOWN, returning empty ratings for user: {}", userId);
        return List.of();
    }

    @CircuitBreaker(name = "ratingServiceCB", fallbackMethod = "fetchRatingsByUserIdsFallback")
    public List<Rating> fetchRatingsByUserIds(Set<String> userIds) {
        log.info("Fetching ratings for {} users", userIds.size());
        return ratingService.getRatingsByUserIds(userIds);
    }

    public List<Rating> fetchRatingsByUserIdsFallback(Set<String> userIds, Exception ex) {
        log.warn("RatingService DOWN, returning empty ratings");
        return List.of();
    }

    @CircuitBreaker(name = "hotelServiceCB", fallbackMethod = "fetchHotelFallback")
    public ResponseEntity<ApiResponse<Hotel>> fetchHotel(String hotelId) {
        log.info("Fetching hotel: {}", hotelId);
        return hotelService.getHotel(hotelId);
    }

    public ResponseEntity<ApiResponse<Hotel>> fetchHotelFallback(String hotelId, Exception ex) {
        log.warn("HotelService DOWN for hotel: {}", hotelId);
        return ResponseEntity.ok(ApiResponse.<Hotel>builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message("Hotel service unavailable")
                .data(null)
                .build());
    }

    @CircuitBreaker(name = "hotelServiceCB", fallbackMethod = "fetchHotelsByIdsFallback")
    public List<Hotel> fetchHotelsByIds(Set<String> hotelIds) {
        log.info("Fetching {} hotels", hotelIds.size());
        return hotelService.getHotelsByIds(hotelIds);
    }

    public List<Hotel> fetchHotelsByIdsFallback(Set<String> hotelIds, Exception ex) {
        log.warn("HotelService DOWN, returning empty hotels");
        return List.of();
    }

    @Override
    public Users saveUser(Users user) {
        user.setUserId(java.util.UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found with id :" + userId));
        userRepository.delete(user);
    }

    @Override
    public Users updateUser(Users user, String userId) {
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found with id :" + userId));
        existingUser.setAbout(user.getAbout());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }
}
