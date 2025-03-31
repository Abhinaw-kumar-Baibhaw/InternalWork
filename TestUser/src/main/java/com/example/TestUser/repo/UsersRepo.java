package com.example.TestUser.repo;

import com.example.TestUser.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("jpaUsersRepo")
public interface UsersRepo extends JpaRepository<Users,Long> {
    Users findByEmail(String username);
}
