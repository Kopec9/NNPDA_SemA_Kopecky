package org.example.nnpda_sema_kopecky.runner;

import org.example.nnpda_sema_kopecky.entity.AppUser;
import org.example.nnpda_sema_kopecky.entity.MeasuringDevice;
import org.example.nnpda_sema_kopecky.entity.Sensor;
import org.example.nnpda_sema_kopecky.repository.AppUserRepository;
import org.example.nnpda_sema_kopecky.repository.MeasuringDeviceRepository;
import org.example.nnpda_sema_kopecky.repository.SensorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRunner implements CommandLineRunner{

    private final AppUserRepository appUserRepository;

    private final MeasuringDeviceRepository measuringDeviceRepository;

    private final SensorRepository sensorRepository;
    private final PasswordEncoder passwordEncoder;


    public DatabaseRunner(AppUserRepository appUserRepository, MeasuringDeviceRepository measuringDeviceRepository, SensorRepository sensorRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.measuringDeviceRepository = measuringDeviceRepository;
        this.sensorRepository = sensorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        AppUser newUser = new AppUser();
        newUser.setUsername("Martin");
        newUser.setEmail("Martin@seznam.cz");
        newUser.setPassword(passwordEncoder.encode("zahrada"));
        appUserRepository.save(newUser);

        AppUser newUser1 = new AppUser();
        newUser1.setUsername("Lukáš");
        newUser1.setEmail("lukáš@seznam.cz");
        newUser1.setPassword(passwordEncoder.encode("rokle"));
        appUserRepository.save(newUser1);

        MeasuringDevice device = new MeasuringDevice();
        device.setDeviceDescription("Na měření intenzity světla");
        device.setAppUser(newUser);
        measuringDeviceRepository.save(device);

        Sensor sensor = new Sensor();
        sensor.setSensorDescription("Intenzita světla č.1");
        sensor.setMeasuringDevice(device);
        sensorRepository.save(sensor);
    }
}
