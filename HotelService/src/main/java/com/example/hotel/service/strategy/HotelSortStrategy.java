package com.example.hotel.service.strategy;

import com.example.hotel.service.entity.Hotel;

import java.util.List;

public interface HotelSortStrategy {

    List<Hotel> sort(List<Hotel> hotels);
    String getName();

}
