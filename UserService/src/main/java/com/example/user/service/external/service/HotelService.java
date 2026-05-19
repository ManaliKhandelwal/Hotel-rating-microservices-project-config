package com.example.user.service.external.service;

import com.example.user.service.entity.Hotel;
import com.example.user.service.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@FeignClient(value = "HOTELSERVICE" , path = "/api/v1/hotels")
public interface HotelService {

    /*  we can use this ApiResponse and ResponseEntity but for that type of responses we need to deserialize
        it with getBody() for ResponseEntity and getData() for ApiRespionses. */


    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponse<Hotel>> getHotel(@PathVariable String hotelId);

    @GetMapping("/")
    public List<Hotel> getAll();

    @PostMapping("/bulk")
    List<Hotel> getHotelsByIds(Set<String> hotelIds);

}
