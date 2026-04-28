package com.example.webserver.service;

import com.example.webserver.entity.RefreshToken;
import com.example.webserver.entity.UserEntity;
import com.example.webserver.exception.TokenExpiredException;
import com.example.webserver.exception.TokenReuseException;
import com.example.webserver.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final long refreshTokenDurationMs = 7 * 24 * 60 * 60 * 1000;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(UserEntity user) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token) {
        
        if (token == null || token.isBlank()) {
            
            throw new IllegalArgumentException("Refresh token is required");
    }
    
        return refreshTokenRepository.findByToken(token)
            .orElseThrow(() -> new TokenReuseException("Refresh token reuse detected"));
}

    public void deleteByUser(UserEntity user) {
        refreshTokenRepository.deleteByUser(user);
    }

    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        refreshTokenRepository.delete(oldToken);
        return createRefreshToken(oldToken.getUser());
    }

    public void verifyExpiration(RefreshToken token) {

    if (token.getExpiryDate().isBefore(Instant.now())) {
        refreshTokenRepository.delete(token);
        throw new TokenExpiredException("Refresh token expired");
    }
}
}