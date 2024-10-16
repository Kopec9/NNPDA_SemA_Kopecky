package org.example.nnpda_sema_kopecky.repository;

import org.example.nnpda_sema_kopecky.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);

}
