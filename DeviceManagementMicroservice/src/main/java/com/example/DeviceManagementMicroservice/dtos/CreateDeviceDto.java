package com.example.DeviceManagementMicroservice.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeviceDto {
    private String description;
    private String address;
    private float maxHourlyEnergyConsumption;
    private UUID userId;
}
