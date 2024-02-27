package com.example.communicationmicroservice.controllers;

import com.example.communicationmicroservice.model.EnergyData;
import com.example.communicationmicroservice.services.EnergyDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("energy")
@RequiredArgsConstructor
public class EnergyController {
    private final EnergyDataService energyService;

    @GetMapping("/consumption")
    public ResponseEntity<List<EnergyData>> getEnergyConsumption(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam UUID deviceId) {
        List<EnergyData> hourlyData = energyService.getHourlyEnergyData(LocalDate.from(date.atStartOfDay()), deviceId);

        System.out.println("Fetched data from the repository: " + hourlyData);
        return ResponseEntity.ok(hourlyData);
    }

}
