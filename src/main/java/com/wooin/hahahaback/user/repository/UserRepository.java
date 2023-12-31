package com.wooin.hahahaback.user.repository;

import com.wooin.hahahaback.user.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long kakaoId);

    @Cacheable(value = "User", key = "#username", unless = "#result==null")
    Optional<User> findByUsername(String username);
}