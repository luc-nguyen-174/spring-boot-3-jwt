package com.example.securityspringboot3.service.token;

import com.example.securityspringboot3.entity.RefreshToken;

import java.util.Optional;

public interface IRefreshTokenService {
    Optional<RefreshToken> findByToken();
    RefreshToken createRefreshToken();
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteRefreshToken(String token);
}
