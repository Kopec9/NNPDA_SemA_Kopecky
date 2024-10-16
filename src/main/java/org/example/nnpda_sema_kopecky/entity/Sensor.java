package org.example.nnpda_sema_kopecky.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String sensorDescription;

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private MeasuringDevice measuringDevice;

}
