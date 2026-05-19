package com.example.hotel.service.repositories;

import com.example.hotel.service.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {

    Page<Hotel> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Hotel> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    Page<Hotel> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String name, String location, Pageable pageable);

    List<Hotel> findHotelsByIdIn(Set<String> hotelIds);
}
