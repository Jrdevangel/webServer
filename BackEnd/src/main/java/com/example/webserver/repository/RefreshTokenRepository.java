package com.example.webserver.repository;

import com.example.webserver.entity.RefreshToken;

import com.example.webserver.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(UserEntity user);
}