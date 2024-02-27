package com.example.monitoringmicroservice.controllers;

import com.example.monitoringmicroservice.rabbitmq.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProducerController {

    private final MessageProducer messageProducer;

    @GetMapping("/start-sending")
    public void startSending() {
        messageProducer.enableScheduledExecution();
        System.out.println("Enabled scheduling.");
        messageProducer.sendMessage(); // Optionally send a message immediately when starting
    }

    @GetMapping("/stop-sending")
    public void stopSending() {
        messageProducer.disableScheduledExecution();
        System.out.println("Disabled scheduling.");
    }
}
