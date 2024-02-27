package com.example.DeviceManagementMicroservice.repositories;

import com.example.DeviceManagementMicroservice.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByUserId(UUID userId);
}
