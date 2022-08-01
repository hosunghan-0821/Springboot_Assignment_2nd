package com.sparta.myblog.repository;

import com.sparta.myblog.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Long> {

    Optional<UserInfo> findByUsername(String username);
    Optional<UserInfo> findByNickname(String nickname);
}
