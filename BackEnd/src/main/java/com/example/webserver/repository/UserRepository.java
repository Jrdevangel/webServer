package com.example.webserver.repository;

import com.example.webserver.domain.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByUsername(String username);
}
