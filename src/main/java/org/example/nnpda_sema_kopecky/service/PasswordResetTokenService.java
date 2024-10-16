package org.example.nnpda_sema_kopecky.service;

import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.example.nnpda_sema_kopecky.entity.PasswordResetToken;
import org.example.nnpda_sema_kopecky.repository.AppUserRepository;
import org.example.nnpda_sema_kopecky.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createPasswordResetTokenForUser(String email) {
        AppUser user = appUserRepository.findByEmail(email);

        if (user != null) {
            // Vygenerování náhodného tokenu
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setAppUser(user);
            resetToken.setExpirationTime(LocalDateTime.now().plusMinutes(30)); // Token platný 30 minut

            tokenRepository.save(resetToken);

            // Odeslání tokenu na email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Resetování hesla");
            mailMessage.setText("Váš resetovací kód je: " + token);
            mailSender.send(mailMessage);

        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void updatePassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null) {
            throw new RuntimeException("Invalid token");
        }

        // Kontrola, zda token nevypršel
        if (resetToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            throw new RuntimeException("Token is expired");
        }

        AppUser user = resetToken.getAppUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(user);

        tokenRepository.delete(resetToken);
    }
}
