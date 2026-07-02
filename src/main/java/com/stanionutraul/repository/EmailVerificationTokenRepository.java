package com.stanionutraul.repository;

import com.stanionutraul.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Integer> {
    Optional<EmailVerificationToken> findByToken(String token);

    List<EmailVerificationToken> findByUserId(Integer userId);
}