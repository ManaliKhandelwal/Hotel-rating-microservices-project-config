package com.example.hotel.service.service.impl;

import com.example.hotel.service.entity.Hotel;
import com.example.hotel.service.exception.ResourceNotFoundException;
import com.example.hotel.service.repositories.HotelRepository;
import com.example.hotel.service.service.HotelService;
import com.example.hotel.service.strategy.HotelSortStrategy;
import com.example.hotel.service.strategy.HotelSortStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    private final HotelSortStrategyFactory strategyFactory;


    public Hotel getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found !!"));
    }

    @Override
    public void updateHotel(String hotelId, String name, String location, String about) {

    }

    @Override
    @Transactional
    public Hotel createHotel(Hotel hotel) {
        if(hotel.getId() == null || hotel.getId().isBlank()){
            String hotelId = UUID.randomUUID().toString();
            hotel.setId(hotelId);
        }

        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> getHotelsSorted(String sortBy) {
        List<Hotel> hotels = hotelRepository.findAll();
        HotelSortStrategy strategy = strategyFactory.getStrategy(sortBy);
        return strategy.sort(hotels);
    }

    @Override
    public Page<Hotel> getHotelsWithPagination(String name, String location, Pageable pageable) {
        if (name != null && !name.isBlank() && location != null && !location.isBlank()) {
            return hotelRepository.findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location, pageable);
        } else if (name != null && !name.isBlank()) {
            return hotelRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (location != null && !location.isBlank()) {
            return hotelRepository.findByLocationContainingIgnoreCase(location, pageable);
        }
        return hotelRepository.findAll(pageable);
    }

    @Override
    public List<Hotel> getHotelsByIds(Set<String> hotelIds) {
        return hotelRepository.findHotelsByIdIn(hotelIds);
    }

}
