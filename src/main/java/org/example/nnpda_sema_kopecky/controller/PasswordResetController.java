package org.example.nnpda_sema_kopecky.controller;

import org.example.nnpda_sema_kopecky.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenService passwordResetService;

    @PostMapping("/reset")
    public String resetPassword(@RequestParam String email) {
        passwordResetService.createPasswordResetTokenForUser(email);
        return "A verification code has been sent to your email.";
    }

    @PutMapping("/confirm-reset")
    public String confirmResetPassword(@RequestParam String token, @RequestParam String newPassword) {
        passwordResetService.updatePassword(token, newPassword);
        return "Password reset was successful.";
    }
}
