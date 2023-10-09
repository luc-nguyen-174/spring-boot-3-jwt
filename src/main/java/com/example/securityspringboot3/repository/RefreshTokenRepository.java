package com.example.securityspringboot3.repository;

import com.example.securityspringboot3.entity.RefreshToken;
import com.example.securityspringboot3.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    void deleteByToken(String token);

    @Modifying
    void deleteByUser(UserInfo user);
}
