package com.example.user.service.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<U> {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private U data;
}
