package br.com.gnobroga.rabbitmq.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Configuration
@Data
public class RabbitMQConfig {
    
    private String queueName = "app.v1.orders";
    private String exchangeName = "app";

    @Bean
    Queue queueOrders() {
        return new Queue(queueName);
    }

    @Bean
    FanoutExchange appExchange() {
        return new FanoutExchange(exchangeName);
    }

    @Bean
    Binding appBinding(FanoutExchange fanoutExchange, Queue queue) {
        return new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, queueName, null);
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> applicationListenerReadyEvent(RabbitAdmin rabbitAdmin) {
        return event ->  rabbitAdmin.initialize();
    }
}
