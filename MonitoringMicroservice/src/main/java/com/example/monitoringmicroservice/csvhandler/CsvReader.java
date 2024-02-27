package com.example.monitoringmicroservice.csvhandler;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {

    public List<Float> readCsv(String csvPath) {
        List<Float> measurements = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String csvLine;

            while ((csvLine = reader.readLine()) != null) {
                float measurementValue = Float.parseFloat(csvLine.trim());
                measurements.add(measurementValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return measurements;
    }
}
