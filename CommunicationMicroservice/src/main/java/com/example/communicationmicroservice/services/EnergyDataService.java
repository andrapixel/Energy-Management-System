package com.example.communicationmicroservice.services;

import com.example.communicationmicroservice.model.EnergyData;
import com.example.communicationmicroservice.repositories.EnergyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnergyDataService {
    private final EnergyDataRepository energyDataRepository;

    public List<EnergyData> getHourlyEnergyData(LocalDate date, UUID deviceId) {
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
        long startTimestamp = startOfDay.toEpochSecond(ZoneOffset.UTC);
        long endTimestamp = endOfDay.toEpochSecond(ZoneOffset.UTC);

        List<EnergyData> hourlyData = new ArrayList<>();
        List<EnergyData> allEnergyData = energyDataRepository.findByDeviceId(deviceId);
        for (EnergyData energyData : allEnergyData) {
            long timestamp = energyData.getTimestamp();
            if (timestamp >= startTimestamp && timestamp <= endTimestamp) {
                hourlyData.add(energyData);
            }
        }

        return hourlyData;
    }
}
