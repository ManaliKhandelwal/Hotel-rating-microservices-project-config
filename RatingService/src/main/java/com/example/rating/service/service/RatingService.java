package com.example.rating.service.service;

import com.example.rating.service.entity.Rating;

import java.util.List;
import java.util.Set;

public interface RatingService {

    //create
    Rating create(Rating rating);


    //get all ratings
    List<Rating> getRatings();

    //get all by UserId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);

    List<Rating> getRatingByUserIds(Set<String> userIds);
}
