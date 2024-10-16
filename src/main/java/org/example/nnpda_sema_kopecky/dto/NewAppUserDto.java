package org.example.nnpda_sema_kopecky.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAppUserDto {
    @NotBlank(message = "Uživatelské jméno je povinné.")
    private String username;
    @NotBlank(message = "Email je povinný.")
    private String email;
    @NotBlank(message = "Heslo je povinné.")
    private String password;

    @JsonCreator
    public NewAppUserDto(@JsonProperty("username")String username, @JsonProperty("email")String email,
                         @JsonProperty("password")String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
