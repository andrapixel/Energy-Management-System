package com.example.communicationmicroservice.repositories;

import com.example.communicationmicroservice.dtos.EnergyDataDto;
import com.example.communicationmicroservice.model.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnergyDataRepository extends JpaRepository<EnergyData, UUID> {
    List<EnergyData> findByDeviceId(UUID deviceId);
}

