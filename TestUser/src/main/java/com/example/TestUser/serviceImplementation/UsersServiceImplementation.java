package com.example.TestUser.serviceImplementation;

import com.example.TestUser.dto.UserDTO;
import com.example.TestUser.model.Users;
import com.example.TestUser.repo.UsersRepo;
import com.example.TestUser.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UsersServiceImplementation implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImplementation.class);

    @Autowired
    private UsersRepo usersRepo;


    private UserDTO convertToDTO(Users users) {
        return new UserDTO(users.getId(), users.getName(), users.getEmail(), users.getRole());
    }

    @Override
    public ResponseEntity<UserDTO> createUser(Users users) {
        logger.info("Creating a new user with email: {}", users.getEmail());
        String password = users.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        users.setPassword(encodedPassword);
        Users createdUser = usersRepo.save(users);
        logger.info("User created successfully with ID: {}", createdUser.getId());
        return new ResponseEntity<>(convertToDTO(createdUser), HttpStatus.CREATED);
    }

}