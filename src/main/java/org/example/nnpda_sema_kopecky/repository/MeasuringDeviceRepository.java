package org.example.nnpda_sema_kopecky.repository;

import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.example.nnpda_sema_kopecky.entity.MeasuringDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasuringDeviceRepository extends JpaRepository<MeasuringDevice, Integer> {

    MeasuringDevice findById(int id);

    List<MeasuringDevice> findByAppUser(AppUser appUser);
}
