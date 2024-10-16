package org.example.nnpda_sema_kopecky.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordDto {
    @NotBlank(message = "Heslo je povinné.")
    private String oldPassword;
    @NotBlank(message = "Heslo je povinné.")
    private String newPassword;

    @JsonCreator
    public NewPasswordDto(@JsonProperty("oldPassword")String oldPassword, @JsonProperty("newPassword")String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
