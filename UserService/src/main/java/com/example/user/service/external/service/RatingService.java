package com.example.user.service.external.service;

import com.example.user.service.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@FeignClient(value = "RATINGSERVICE" , path = "/api/v1/ratings")
public interface RatingService {

    @GetMapping("/users/{userId}")
    public List<Rating> getRatings(@PathVariable String userId);

    @GetMapping("/")
    public List<Rating> getAll();

    @PostMapping("/bulk")
    List<Rating> getRatingsByUserIds(Set<String> userIds);


}
