package com.example.communicationmicroservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    Queue queue() {
        return new Queue("test-queue", true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("test-exchange");
    }

    @Bean
    Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("test-queue");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        // Cloud
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqps://gfhtpohk:aZZSJEDKmFP_x868cVyI--DwMF_oc4af@sparrow.rmq.cloudamqp.com/gfhtpohk"); //"amqps://sparrow.rmq.cloudamqp.com/gfhtpohk");
        connectionFactory.setUsername("gfhtpohk");
        connectionFactory.setPassword("aZZSJEDKmFP_x868cVyI--DwMF_oc4af");
        connectionFactory.setPort(5671);
        connectionFactory.setVirtualHost("gfhtpohk");

        return connectionFactory;
    }
}
