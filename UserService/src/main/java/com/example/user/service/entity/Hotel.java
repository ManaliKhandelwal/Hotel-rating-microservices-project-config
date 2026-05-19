package com.example.user.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    private  String id;

    private  String name;

    private  String location;

    private  String about;

    private LocalDateTime createdAt = LocalDateTime.now();

}
