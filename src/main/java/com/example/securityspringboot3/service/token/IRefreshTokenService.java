package com.example.securityspringboot3.service.token;

import com.example.securityspringboot3.entity.RefreshToken;
import com.example.securityspringboot3.exception.TokenRefreshException;

import java.util.Optional;

public interface IRefreshTokenService {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token) throws TokenRefreshException;
    void deleteRefreshTokenByUserId(Long userId);
}
