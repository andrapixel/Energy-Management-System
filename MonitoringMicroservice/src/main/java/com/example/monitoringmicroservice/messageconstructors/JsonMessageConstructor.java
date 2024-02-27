package com.example.monitoringmicroservice.messageconstructors;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JsonMessageConstructor {

    public String constructMessage(long timestamp, UUID deviceId, Float measurement) {
        JsonObject jsonMessage = new JsonObject();
        jsonMessage = new JsonObject();
        jsonMessage.addProperty("timestamp", timestamp);
        jsonMessage.addProperty("device_id", deviceId.toString());
        jsonMessage.addProperty("measurement_value", measurement);

        return jsonMessage.toString();
    }
}
