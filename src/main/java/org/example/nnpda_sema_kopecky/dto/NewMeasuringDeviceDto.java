package org.example.nnpda_sema_kopecky.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NewMeasuringDeviceDto {

    @NotBlank(message = "Popis je povinný.")
    private String deviceDescription;

    private int appUserId;
    @JsonCreator
    public NewMeasuringDeviceDto(@JsonProperty("deviceDescription")String deviceDescription, @JsonProperty("appUserId")int appUserId) {
        this.appUserId = appUserId;
        this.deviceDescription = deviceDescription;
    }
}
