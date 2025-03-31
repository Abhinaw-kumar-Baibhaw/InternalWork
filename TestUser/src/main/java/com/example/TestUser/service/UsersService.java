package com.example.TestUser.service;


import com.example.TestUser.dto.UserDTO;
import com.example.TestUser.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {

    ResponseEntity<UserDTO> createUser(Users users);
}
