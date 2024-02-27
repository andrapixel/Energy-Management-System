package com.example.monitoringmicroservice.rabbitmq;

import com.example.monitoringmicroservice.config.DeviceConfig;
import com.example.monitoringmicroservice.csvhandler.CsvReader;
import com.example.monitoringmicroservice.messageconstructors.JsonMessageConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final CsvReader csvReader;
    private final JsonMessageConstructor jsonMessageConstructor;
    private final DeviceConfig deviceConfig;
    private UUID previousDeviceId = null;
    private boolean scheduledExecutionEnabled = false;
    private List<UUID> existingDevices = new ArrayList<>();
    private Map<UUID, Integer> csvIndexMap = new HashMap<>();

    public void enableScheduledExecution() {
        scheduledExecutionEnabled = true;
    }
    public void disableScheduledExecution() {
        scheduledExecutionEnabled = false;
    }

    // 600000 = 10 min, 60000 = 1 min
    @Scheduled(fixedDelay = 60000)
    public void sendPeriodicMessages() {
        if (scheduledExecutionEnabled) {
            sendMessage();
        }
    }

    public void sendMessage() {
        UUID currentDeviceId = deviceConfig.getDeviceId();
        if (currentDeviceId == null) {
            throw new NoSuchElementException();
        }

        csvIndexMap.putIfAbsent(currentDeviceId, 0);

        // check if the monitored device has changed
        if (!currentDeviceId.equals(previousDeviceId) && !existingDevices.contains(currentDeviceId)) {
            csvIndexMap.put(currentDeviceId, 0);
        }

        List<Float> measurements = csvReader.readCsv("./sensor.csv");
        long currentTimestamp = Instant.now().toEpochMilli();

        int csvIndex = csvIndexMap.get(currentDeviceId);

        if (!measurements.isEmpty() && csvIndex < measurements.size()) {
            Float measurement = measurements.get(csvIndex);
            String jsonMsg = jsonMessageConstructor.constructMessage(currentTimestamp, currentDeviceId, measurement);
            rabbitTemplate.convertAndSend("test-queue", jsonMsg);
            System.out.println("Message sent to queue: " + jsonMsg);
            existingDevices.add(currentDeviceId);

            csvIndexMap.put(currentDeviceId, csvIndex + 1);
        }
    }
}
