package com.example.DeviceManagementMicroservice.services;

import com.example.DeviceManagementMicroservice.dtos.CreateDeviceDto;
import com.example.DeviceManagementMicroservice.dtos.DeviceDto;
import com.example.DeviceManagementMicroservice.dtos.mappers.DeviceMapper;
import com.example.DeviceManagementMicroservice.models.Device;
import com.example.DeviceManagementMicroservice.exceptions.DeviceNotFoundException;
import com.example.DeviceManagementMicroservice.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public List<DeviceDto> getDevices() {
        List<Device> deviceList = deviceRepository.findAll();

        return deviceList.stream()
                .map(DeviceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<DeviceDto> getDevicesByUser(UUID userId) {
        List<Device> deviceList = deviceRepository.findByUserId(userId);

        return deviceList.stream()
                .map(DeviceMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public DeviceDto getDeviceById(UUID deviceId) {
        Optional<Device> foundDevice = deviceRepository.findById(deviceId);
        if (foundDevice.isEmpty()) {
            throw new DeviceNotFoundException("Device with this UUID could not be found.");
        }

        return DeviceMapper.mapToDto(foundDevice.get());
    }

    public DeviceDto createDevice(CreateDeviceDto deviceDto) {
        if (deviceDto == null) {
            throw new IllegalArgumentException("Invalid device data.");
        }

        Device newDevice = new Device();
        newDevice.setDescription(deviceDto.getDescription());
        newDevice.setAddress(deviceDto.getAddress());
        newDevice.setMaxHourlyEnergyConsumption(deviceDto.getMaxHourlyEnergyConsumption());
        newDevice.setUserId(deviceDto.getUserId());
        deviceRepository.save(newDevice);

        return DeviceMapper.mapToDto(newDevice);
    }

    public String updateDevice(UUID deviceId, CreateDeviceDto givenDto) {
        Optional<Device> foundDevice = deviceRepository.findById(deviceId);
        if (foundDevice.isEmpty()) {
            throw new DeviceNotFoundException("Device with ID = " + deviceId + " could not be found.");
        }

        Device updatedDevice = foundDevice.get();
        updatedDevice.setDescription(givenDto.getDescription());
        updatedDevice.setAddress(givenDto.getAddress());
        updatedDevice.setMaxHourlyEnergyConsumption(givenDto.getMaxHourlyEnergyConsumption());
        updatedDevice.setUserId(givenDto.getUserId());
        deviceRepository.save(updatedDevice);

        return "Device details were updated successfully.";
    }

    public String deleteDevice(UUID deviceId) {
        Optional<Device> foundDevice = deviceRepository.findById(deviceId);
        if (foundDevice.isEmpty()) {
            throw new DeviceNotFoundException("Device with ID = " + deviceId + " could not be found.");
        }

        deviceRepository.deleteById(deviceId);
        return "Device removed successfully.";
    }

    public String deleteDevicesOfUser(UUID userId) {
        List<DeviceDto> devicesToDelete = getDevicesByUser(userId);

        for (DeviceDto dto : devicesToDelete) {
            deleteDevice(dto.getDeviceId());
        }

        return "Devices of user " + userId + " were removed successfully.";
    }
}
