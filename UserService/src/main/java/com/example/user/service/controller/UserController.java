package com.example.user.service.controller;

import com.example.user.service.entity.Rating;
import com.example.user.service.entity.Users;
import com.example.user.service.payload.ApiResponse;
import com.example.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/api/v1/users")
@RestController
public class UserController {

//    private static final String RATING_SERVICE = "ratingServiceCircuitBreaker";
private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Users>> getUser(@PathVariable String userId) {
        Users user = userService.getUserById(userId);
        ApiResponse<Users> response =  ApiResponse.<Users>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("User fetched successfully")
                .data(user)
                .build();
        log.info("working okay");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Users>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size
    ) {
        Page<Users> users = userService.getAllUser(page,size);
        ApiResponse<Page<Users>> response =  ApiResponse.<Page<Users>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("Users fetched successfully")
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Users>> updateUser(@RequestBody Users user, @PathVariable String userId) {
        Users updatedUser = userService.updateUser(user, userId);
        ApiResponse<Users> response =  ApiResponse.<Users>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("User Updated Successfully")
                .data(updatedUser)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteUser(@RequestParam String userId) {
        userService.deleteUser(userId);
        ApiResponse<Void> response =  ApiResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .message("User Deleted Successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }


}
