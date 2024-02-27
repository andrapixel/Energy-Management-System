package com.example.DeviceManagementMicroservice.dtos.mappers;

import com.example.DeviceManagementMicroservice.dtos.DeviceDto;
import com.example.DeviceManagementMicroservice.models.Device;

public class DeviceMapper {
    public static DeviceDto mapToDto(Device deviceEntity) {
        return new DeviceDto(deviceEntity.getDeviceId(), deviceEntity.getDescription(), deviceEntity.getAddress(),
                deviceEntity.getMaxHourlyEnergyConsumption(), deviceEntity.getUserId());
    }
}
