package org.example.nnpda_sema_kopecky.repository;

import org.example.nnpda_sema_kopecky.entity.MeasuringDevice;
import org.example.nnpda_sema_kopecky.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    Sensor findById(int id);

    List<Sensor> findByMeasuringDevice(MeasuringDevice device);

}
