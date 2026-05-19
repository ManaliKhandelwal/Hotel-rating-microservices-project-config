package com.example.hotel.service.service;

import com.example.hotel.service.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface HotelService {

    Hotel getHotelById(String hotelId);

    void updateHotel(String hotelId, String name, String location, String about);

    Hotel createHotel(Hotel hotel);

    List<Hotel> getAllHotels();

    List<Hotel> getHotelsSorted(String sortBy);

    Page<Hotel> getHotelsWithPagination(String name, String location, Pageable pageable);

    List<Hotel> getHotelsByIds(Set<String> hotelIds);
}
