package com.example.DeviceManagementMicroservice.controllers;

import com.example.DeviceManagementMicroservice.dtos.CreateDeviceDto;
import com.example.DeviceManagementMicroservice.dtos.DeviceDto;
import com.example.DeviceManagementMicroservice.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping("/list")
    public ResponseEntity<List<DeviceDto>> getDevices() {
        List<DeviceDto> dtos = deviceService.getDevices();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceDto> getDeviceById(@PathVariable UUID deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    @GetMapping("max-energy/{deviceId}")
    public ResponseEntity<Float> getMaxEnergyConsumptionByDeviceId(@PathVariable UUID deviceId) {
        DeviceDto deviceDto = deviceService.getDeviceById(deviceId);
        return ResponseEntity.ok(deviceDto.getMaxHourlyEnergyConsumption());
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<DeviceDto>> getDevices(@PathVariable UUID userId) {
        List<DeviceDto> dtos = deviceService.getDevicesByUser(userId);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/add")
    public ResponseEntity<DeviceDto> addDevice(@RequestBody CreateDeviceDto createDeviceDto) {
        return ResponseEntity.ok(deviceService.createDevice(createDeviceDto));
    }

    @PutMapping("/update/{deviceId}")
    public ResponseEntity<String> updateDevice(@PathVariable UUID deviceId, @RequestBody CreateDeviceDto givenDto) {
        return ResponseEntity.ok(deviceService.updateDevice(deviceId, givenDto));
    }

    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable UUID deviceId) {
        return ResponseEntity.ok(deviceService.deleteDevice(deviceId));
    }

    @DeleteMapping("/delete-devices/{userId}")
    public ResponseEntity<String> deleteDevicesOfUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(deviceService.deleteDevicesOfUser(userId));
    }
}
