package com.example.webserver.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.webserver.domain.Role;
import com.example.webserver.domain.UserEntity;
import com.example.webserver.repository.JpaUserRepository;

@Configuration
@Profile("!dev")
public class DataInitializer {

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            userRepository.save(
                new UserEntity(
                    "admin",
                    "{noop}adminpass",
                    Role.ADMIN
                )
            );

            userRepository.save(
                new UserEntity(
                    "user",
                    "{noop}userpass",
                    Role.USER
                )
            );
        }
    }

    @Bean
    CommandLineRunner initUsers(
            JpaUserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (userRepository.count() == 0) {

                userRepository.save(
                    new UserEntity(
                        "admin",
                        passwordEncoder.encode("adminpass"),
                        Role.ADMIN
                    )
                );

                userRepository.save(
                    new UserEntity(
                        "user",
                        passwordEncoder.encode("userpass"),
                        Role.USER
                    )
                );
            }
        };
    }
}
