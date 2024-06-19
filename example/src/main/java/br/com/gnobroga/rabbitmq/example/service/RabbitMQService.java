package br.com.gnobroga.rabbitmq.example.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.gnobroga.rabbitmq.example.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMQService {
    
    private final RabbitTemplate template;

    private final RabbitMQConfig rabbitMQConfig;

    public void send(String content) {
        template.convertAndSend(rabbitMQConfig.getExchangeName(), rabbitMQConfig.getQueueName(), content);
    }


}
