package org.example.nnpda_sema_kopecky.service;

import org.example.nnpda_sema_kopecky.dto.NewSensorDto;
import org.example.nnpda_sema_kopecky.dto.UpdateSensorDto;
import org.example.nnpda_sema_kopecky.entity.MeasuringDevice;
import org.example.nnpda_sema_kopecky.entity.Sensor;
import org.example.nnpda_sema_kopecky.repository.MeasuringDeviceRepository;
import org.example.nnpda_sema_kopecky.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SensorService {

    @Autowired
    private MeasuringDeviceRepository measuringDeviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public Collection<Sensor> getAllSensors(){
        return sensorRepository.findAll();
    }

    public Collection<Sensor> getSensorsByDevice(MeasuringDevice device){
        return sensorRepository.findByMeasuringDevice(device);
    }
    public void createSensor(NewSensorDto newSensor){
        Sensor newSens = new Sensor();

        if (measuringDeviceRepository.existsById(newSensor.getDeviceId())) {
            newSens.setSensorDescription(newSensor.getSensorDescription());
            newSens.setMeasuringDevice(measuringDeviceRepository.findById(newSensor.getDeviceId()));
            sensorRepository.save(newSens);
        } else {
            throw new RuntimeException("Device not found");
        }
    }

    public void updateSensor(int id, UpdateSensorDto updatedSensor){
        Sensor existingSensor = sensorRepository.findById(id);
        if (existingSensor != null) {
            existingSensor.setSensorDescription(updatedSensor.getSensorDescription());
            existingSensor.setMeasuringDevice(measuringDeviceRepository.findById(updatedSensor.getDeviceId()));
            sensorRepository.save(existingSensor);
        } else {
            throw new RuntimeException("Sensor not Found");
        }
    }

    public void deleteSensor(int id){
        if (sensorRepository.existsById(id)) {
            sensorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sensor not found");
        }
    }

    public Sensor findById(int id){
        return sensorRepository.findById(id);
    }
}
