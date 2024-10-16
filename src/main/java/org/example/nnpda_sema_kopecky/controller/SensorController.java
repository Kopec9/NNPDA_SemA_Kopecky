package org.example.nnpda_sema_kopecky.controller;

import jakarta.validation.Valid;
import org.example.nnpda_sema_kopecky.dto.NewSensorDto;
import org.example.nnpda_sema_kopecky.dto.UpdateSensorDto;
import org.example.nnpda_sema_kopecky.entity.Sensor;
import org.example.nnpda_sema_kopecky.repository.MeasuringDeviceRepository;
import org.example.nnpda_sema_kopecky.repository.SensorRepository;
import org.example.nnpda_sema_kopecky.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/sensor")
public class SensorController {
    @Autowired
    private final SensorRepository sensorRepository;

    @Autowired
    private final MeasuringDeviceRepository measuringDeviceRepository;

    @Autowired
    private SensorService sensorService;

    @Autowired
    public SensorController(SensorRepository sensorRepository, MeasuringDeviceRepository measuringDeviceRepository) {
        this.sensorRepository = sensorRepository;
        this.measuringDeviceRepository = measuringDeviceRepository;
    }

    @GetMapping("/all")
    public Collection<Sensor> getSensors() {
        return sensorService.getAllSensors();
    }

    @GetMapping("/device_sensors/{id}")
    public Collection<Sensor> getSensorsByDevice(@PathVariable int id) {
        return sensorService.getSensorsByDevice(measuringDeviceRepository.findById(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSensorById(@PathVariable int id) {
        Sensor sensor = sensorService.findById(id);

        if (sensor != null) {
            return ResponseEntity.ok(sensor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor with Id " + id + " was not found.");
        }
    }

    @PostMapping("/new")
    public String createSensor(@Valid @RequestBody NewSensorDto newSensor,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Error: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        try {
            sensorService.createSensor(newSensor);
            return "Sensor successfully created";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while adding the sensor";
        }

    }

    @PutMapping("/update/{id}")
    public String updateSensor(@PathVariable int id, @RequestBody UpdateSensorDto updatedSensor,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Error: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        try {
            sensorService.updateSensor(id, updatedSensor);
            return "Sensor successfully created";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while updating the sensor";
        }
    }


    @DeleteMapping("/delete/{id}")
    public String deleteSensor(@PathVariable int id) {

        try {
            sensorService.deleteSensor(id);
            return "Sensor successfully deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while deleting the sensor.";
        }
    }
}
