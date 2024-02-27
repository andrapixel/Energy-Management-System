package com.example.communicationmicroservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        // sockJs is used to enable fallback options for browsers that don't support websockets
        stompEndpointRegistry.addEndpoint("/websocket").setAllowedOrigins("http://localhost:3000", "http://react")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry msgBrokerRegistry) {
        // enables simple message broker to send messages to ALL clients subscribed to "/topic" - ONE-TO-MANY
        msgBrokerRegistry.enableSimpleBroker("/topic");
        // ONE-TO-ONE comm.
        msgBrokerRegistry.setApplicationDestinationPrefixes("/app");
    }
}
