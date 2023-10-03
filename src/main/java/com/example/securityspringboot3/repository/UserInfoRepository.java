package com.example.securityspringboot3.repository;

import com.example.securityspringboot3.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    @Query(
            value = "select exists( select * from userinfo ui join security.user_role ur on ui.id = ur.user_id where ui.username = :username and ur.role_id = (select id from role where name = :role)) as result",
            nativeQuery = true)
    int existsUserInfoByUsernameAndRoles(String username, String role);
}
