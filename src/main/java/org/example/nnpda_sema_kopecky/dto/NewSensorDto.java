package org.example.nnpda_sema_kopecky.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NewSensorDto {
    @NotBlank(message = "Popis je povinný.")
    private String sensorDescription;

    private int deviceId;
    @JsonCreator
    public NewSensorDto(@JsonProperty("sensorDescription")String sensorDescription, @JsonProperty("deviceId")int deviceId) {
        this.deviceId = deviceId;
        this.sensorDescription = sensorDescription;
    }
}
