package com.example.webserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webserver.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
}
