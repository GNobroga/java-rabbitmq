package br.com.gnobroga.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMQConfig {
    
    private static final String queue = "app.v1.orders";
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.queue)
    public void onMessage(final Message message) throws JsonMappingException, JsonProcessingException {
        System.out.println(message.getBody().length);
        final var content = new String(message.getBody());
        System.out.println(content);
    }
}
