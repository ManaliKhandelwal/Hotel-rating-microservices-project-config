package com.example.user.service.service;

import com.example.user.service.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public Page<Users> getAllUser(int page, int size);

    Users getUserById(String userId);

    Users saveUser(Users user);

    void deleteUser(String userId);

    Users updateUser(Users user, String userId);

}
