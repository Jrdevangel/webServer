package com.example.webserver.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.webserver.domain.Role;
import com.example.webserver.domain.UserEntity;
import com.example.webserver.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (userRepository.count() == 0) {

                UserEntity admin = new UserEntity(
                        "admin",
                        passwordEncoder.encode("adminpass"),
                        Role.ADMIN
                );

                UserEntity user = new UserEntity(
                        "user",
                        passwordEncoder.encode("userpass"),
                        Role.USER
                );

                userRepository.save(admin);
                userRepository.save(user);
            }
        };
    }
}
