package com.example.DeviceManagementMicroservice.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private UUID deviceId;
    private String description;
    private String address;
    private float maxHourlyEnergyConsumption;
    private UUID userId;
}
