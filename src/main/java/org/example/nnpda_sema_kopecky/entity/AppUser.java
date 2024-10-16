package org.example.nnpda_sema_kopecky.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Uživatelské jméno nesmí být null.")
    @NotBlank(message = "Uživatelské jméno nesmí být prázdný řetězec.")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE)
    private List<MeasuringDevice> measuringDevice;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.REMOVE)
    private List<PasswordResetToken> resetToken;


}
