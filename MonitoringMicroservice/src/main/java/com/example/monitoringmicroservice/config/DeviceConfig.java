package com.example.monitoringmicroservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@ConfigurationProperties(prefix = "device")
@Getter
@Setter
public class DeviceConfig {
    @Value("${device.device-id}")
    private UUID deviceId;

    public DeviceConfig() {
    }
}
