package org.example.nnpda_sema_kopecky.controller;

import jakarta.validation.Valid;
import org.example.nnpda_sema_kopecky.dto.NewMeasuringDeviceDto;
import org.example.nnpda_sema_kopecky.dto.UpdateMeasuringDeviceDto;
import org.example.nnpda_sema_kopecky.dto.UpdateSensorDto;
import org.example.nnpda_sema_kopecky.entity.MeasuringDevice;
import org.example.nnpda_sema_kopecky.repository.AppUserRepository;
import org.example.nnpda_sema_kopecky.repository.MeasuringDeviceRepository;
import org.example.nnpda_sema_kopecky.service.MeasuringDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/device")
public class MeasuringDeviceController {
    @Autowired
    private final MeasuringDeviceRepository measuringDeviceRepository;

    @Autowired
    private final AppUserRepository appUserRepository;

    @Autowired
    private MeasuringDeviceService measuringDeviceService;

    @Autowired
    public MeasuringDeviceController(AppUserRepository appUserRepository, MeasuringDeviceRepository measuringDeviceRepository) {
        this.appUserRepository = appUserRepository;
        this.measuringDeviceRepository = measuringDeviceRepository;
    }

    @GetMapping("/all")
    public Collection<MeasuringDevice> getMeasuringDevices() {
        return measuringDeviceService.getAllMeasuringDevices();
    }

    @GetMapping("/user_devices/{id}")
    public Collection<MeasuringDevice> getDeviceByAppUser(@PathVariable int id) {
        return measuringDeviceService.findByAppUser(appUserRepository.findById(id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasuringDeviceById(@PathVariable int id) {
        MeasuringDevice measuringDevice = measuringDeviceService.findById(id);

        if (measuringDevice != null) {
            return ResponseEntity.ok(measuringDevice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device with Id " + id + " was not found.");
        }
    }

    @PostMapping("/new")
    public String createMeasuringDevice(@Valid @RequestBody NewMeasuringDeviceDto newDevice,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Error: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        try {
            measuringDeviceService.createMeasuringDevice(newDevice);
            return "Device successfully created";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while adding the device";
        }
    }

    @PutMapping("/update/{id}")
    public String updateMeasuringDevice(@PathVariable int id, @RequestBody UpdateMeasuringDeviceDto updatedDevice,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Error: " + bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        try {
            measuringDeviceService.updateMeasuringDevice(id, updatedDevice);
            return "Device successfully updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while updating the device";
        }
    }


    @DeleteMapping("/delete/{id}")
    public String deleteMeasuringDevice(@PathVariable int id) {

        try {
            measuringDeviceService.deleteMeasuringDevice(id);
            return "Device successfully deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while deleting the device.";
        }
    }
}
