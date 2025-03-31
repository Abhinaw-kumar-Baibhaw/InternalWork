package com.example.TestUser.controller;

import com.example.TestUser.dto.UserDTO;
import com.example.TestUser.model.Users;
import com.example.TestUser.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody Users users) {
        if (users == null || users.getName()== null || users.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ResponseEntity<UserDTO> response = usersService.createUser(users);
        return response;
    }

}
