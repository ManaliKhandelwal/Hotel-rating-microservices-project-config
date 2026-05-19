package com.example.hotel.service.strategy.impl;

import com.example.hotel.service.entity.Hotel;
import com.example.hotel.service.strategy.HotelSortStrategy;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class SortByLocation implements HotelSortStrategy {

    @Override
    public List<Hotel> sort(List<Hotel> hotels) {
        return hotels.stream()
                .sorted(Comparator.comparing(Hotel::getLocation, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    @Override
    public String getName() {
        return "location";
    }
}
