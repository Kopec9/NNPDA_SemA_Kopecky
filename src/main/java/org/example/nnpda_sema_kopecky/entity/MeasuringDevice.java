package org.example.nnpda_sema_kopecky.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class MeasuringDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String deviceDescription;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private AppUser appUser;

    @JsonIgnore
    @OneToMany(mappedBy = "measuringDevice", cascade = CascadeType.REMOVE)
    private List<Sensor> sensors;
}
