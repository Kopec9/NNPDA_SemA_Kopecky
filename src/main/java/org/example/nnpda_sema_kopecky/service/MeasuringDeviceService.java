package org.example.nnpda_sema_kopecky.service;

import org.example.nnpda_sema_kopecky.dto.NewMeasuringDeviceDto;
import org.example.nnpda_sema_kopecky.dto.UpdateMeasuringDeviceDto;
import org.example.nnpda_sema_kopecky.dto.UpdateSensorDto;
import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.example.nnpda_sema_kopecky.entity.MeasuringDevice;
import org.example.nnpda_sema_kopecky.entity.Sensor;
import org.example.nnpda_sema_kopecky.repository.AppUserRepository;
import org.example.nnpda_sema_kopecky.repository.MeasuringDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MeasuringDeviceService {

    @Autowired
    private MeasuringDeviceRepository measuringDeviceRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public Collection<MeasuringDevice> getAllMeasuringDevices(){
        return measuringDeviceRepository.findAll();
    }

    public Collection<MeasuringDevice> findByAppUser(AppUser user){
        return measuringDeviceRepository.findByAppUser(user);
    }
    public void createMeasuringDevice(NewMeasuringDeviceDto newDevice){
        MeasuringDevice newDev = new MeasuringDevice();

        if (appUserRepository.existsById(newDevice.getAppUserId())) {
            newDev.setDeviceDescription(newDevice.getDeviceDescription());
            newDev.setAppUser(appUserRepository.findById(newDevice.getAppUserId()));
            measuringDeviceRepository.save(newDev);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void updateMeasuringDevice(int id, UpdateMeasuringDeviceDto updatedDevice){
        MeasuringDevice existingDevice = measuringDeviceRepository.findById(id);
        if (existingDevice != null) {
            existingDevice.setDeviceDescription(updatedDevice.getDeviceDescription());
            existingDevice.setAppUser(appUserRepository.findById(updatedDevice.getAppUserId()));
            measuringDeviceRepository.save(existingDevice);
        } else {
            throw new RuntimeException("Sensor not Found");
        }
    }

    public void deleteMeasuringDevice(int id){
        if (measuringDeviceRepository.existsById(id)) {
            measuringDeviceRepository.deleteById(id);
        } else {
            throw new RuntimeException("Device not found");
        }
    }

    public MeasuringDevice findById(int id){
        return measuringDeviceRepository.findById(id);
    }
}
