package com.example.webserver.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.webserver.domain.UserEntity;
import com.example.webserver.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}