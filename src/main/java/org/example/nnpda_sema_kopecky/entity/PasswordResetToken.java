package org.example.nnpda_sema_kopecky.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private AppUser appUser;

    @Column(nullable = false)
    private LocalDateTime expirationTime;
}
