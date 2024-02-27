package com.example.communicationmicroservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnergyData {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false, unique = true)
    private UUID dataId;

    @Column(nullable = false, unique = true)
    private long timestamp;

    @Column(nullable = false)
    private UUID deviceId;

    @Column(nullable = false)
    private float measurementValue;
}