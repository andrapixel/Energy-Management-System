package com.example.communicationmicroservice.rabbitmq;

import com.example.communicationmicroservice.model.EnergyData;
import com.example.communicationmicroservice.model.WebSocketMessage;
import com.example.communicationmicroservice.repositories.EnergyDataRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final EnergyDataRepository energyDataRepository;
    private final RestTemplate restTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<UUID, Map<Integer, Float>> hourlyConsumptionMap = new HashMap<>();

    @RabbitListener(queues = "test-queue")
    public void listenMessage(String message) {
        System.out.println("Message read from queue: " + message);

        try {
            JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
            long timestamp = jsonObject.get("timestamp").getAsLong();
            UUID deviceId = UUID.fromString(jsonObject.get("device_id").getAsString());
            float measurementValue = jsonObject.get("measurement_value").getAsFloat();

            // get current hour from timestamp
            LocalDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
            int currentHour = dateTime.getHour();

            // create map for the device
            Map<Integer, Float> hourlyConsumption = hourlyConsumptionMap.computeIfAbsent(deviceId, k -> new HashMap<>());
            float runningSum = hourlyConsumption.getOrDefault(currentHour, 0.0f);
            runningSum += measurementValue;
            hourlyConsumption.put(currentHour, runningSum);

            float maxEnergyConsumption = getMaxEnergyConsumption(deviceId);
            System.out.println(maxEnergyConsumption);
            if (runningSum > maxEnergyConsumption) {
                System.out.println("Hourly energy consumption exceeded the max. energy consumption for device " + deviceId + " in hour" + currentHour);
                sendWebSocketMessage(deviceId, runningSum);
            }

            EnergyData energyData = new EnergyData();
            energyData.setTimestamp(timestamp);
            energyData.setDeviceId(deviceId);
            energyData.setMeasurementValue(measurementValue);

            energyDataRepository.save(energyData);
            System.out.println("Energy data saved to database for device " + deviceId + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float getMaxEnergyConsumption(UUID deviceId) {
        String url = "http://host.docker.internal:8081/api/devices/max-energy/" + deviceId;
        return restTemplate.getForObject(url, Float.class);
    }

    private void sendWebSocketMessage(UUID deviceId, float measurementValue) {
        String destination = "/topic/energy-consumption-notification";
        String message = "Hourly energy consumption exceeded the max. energy consumption for device " + deviceId +
                ". Total consumption: " + measurementValue;
        WebSocketMessage webSocketMessage = new WebSocketMessage(message);
        messagingTemplate.convertAndSend(destination, webSocketMessage);
    }
}
