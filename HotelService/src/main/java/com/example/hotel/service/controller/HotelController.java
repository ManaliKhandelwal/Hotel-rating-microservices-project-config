package com.example.hotel.service.controller;


import com.example.hotel.service.entity.Hotel;
import com.example.hotel.service.payload.ApiResponse;
import com.example.hotel.service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {


    @Autowired
    private HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.createHotel(hotel);
        ApiResponse<Hotel> apiResponse = ApiResponse.<Hotel>builder()
                .data(createdHotel)
                .message("Hotel created successfully")
                .status(HttpStatus.CREATED)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse<Hotel>> getHotel(@PathVariable String hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        ApiResponse<Hotel> apiResponse = ApiResponse.<Hotel>builder()
                .data(hotel)
                .message("Hotel fetched successfully")
                .status(HttpStatus.OK)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Hotel>>> getAll(){
        List<Hotel> hotels = hotelService.getAllHotels();
        ApiResponse<List<Hotel>> apiResponse = ApiResponse.<List<Hotel>>builder()
                .data(hotels)
                .message("Hotels fetched successfully")
                .status(HttpStatus.OK)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Hotel>> getSortedHotels(
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(hotelService.getHotelsSorted(sortBy));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Hotel>> getHotelsWithPagination(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @PageableDefault(size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            }) Pageable pageable) {

        Page<Hotel> page = hotelService.getHotelsWithPagination(name, location, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/bulk")
    public List<Hotel> getHotelsByIds(@RequestBody Set<String> hotelIds){
        return hotelService.getHotelsByIds(hotelIds);
    }
}
