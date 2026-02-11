package com.example.webserver.repository.memory;

import com.example.webserver.domain.Role;
import com.example.webserver.domain.UserEntity;
import com.example.webserver.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@Profile("dev")
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, UserEntity> users = Map.of(
        "admin", new UserEntity("admin", "{noop}adminpass", Role.ADMIN),
        "user", new UserEntity("user", "{noop}userpass", Role.USER)
    );

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }
}
