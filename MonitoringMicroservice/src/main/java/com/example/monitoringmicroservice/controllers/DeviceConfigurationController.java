package com.example.monitoringmicroservice.controllers;

import com.example.monitoringmicroservice.config.DeviceConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/config")
@RequiredArgsConstructor
public class DeviceConfigurationController {
    private final DeviceConfig deviceConfig;

    @PostMapping("/set-device")
    public ResponseEntity<String> setDeviceId(@RequestBody UUID deviceId) {
        deviceConfig.setDeviceId(deviceId);
        System.out.println("Set device Id = " + deviceConfig.getDeviceId());
        return ResponseEntity.ok("Device Id was set successfully in the configuration file.");
    }
}
