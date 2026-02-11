package com.example.webserver.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.webserver.domain.UserEntity;

import java.util.Optional;

@Repository
@Profile("!dev")
public interface JpaUserRepository
        extends JpaRepository<UserEntity, Long>, UserRepository {

    Optional<UserEntity> findByUsername(String username);
}
