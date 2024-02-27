package com.example.communicationmicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnergyDataDto {
    private long timestamp;
    private UUID deviceId;
    private float measurementValue;
}