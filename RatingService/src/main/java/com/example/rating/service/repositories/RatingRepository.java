package com.example.rating.service.repositories;

import com.example.rating.service.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {

    List<Rating> findByUserId(String userId);

    List<Rating> findByHotelId(String hotelId);

    List<Rating> findByUserIdIn( Set<String> userIds);
}
