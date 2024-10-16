package org.example.nnpda_sema_kopecky.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateSensorDto {
    @NotBlank(message = "Popis je povinný.")
    private String sensorDescription;

    @NotBlank(message = "Id zařízení je povinné.")
    private int deviceId;
    @JsonCreator
    public UpdateSensorDto(@JsonProperty("sensorDescription")String sensorDescription, @JsonProperty("deviceId")int deviceId) {
        this.deviceId = deviceId;
        this.sensorDescription = sensorDescription;
    }
}
