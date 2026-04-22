package com.example.webserver.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.webserver.entity.Role;
import com.example.webserver.repository.UserRepository;
import com.example.webserver.entity.UserEntity;

@Configuration
@Profile("local")
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository,
                                PasswordEncoder passwordEncoder){
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