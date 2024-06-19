package br.com.gnobroga.rabbitmq.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gnobroga.rabbitmq.example.entity.OrderEntity;
import br.com.gnobroga.rabbitmq.example.entity.ProductOrderEntity;
import br.com.gnobroga.rabbitmq.example.entity.ProductOrderPK;
import br.com.gnobroga.rabbitmq.example.service.RabbitMQService;
import br.com.gnobroga.rabbitmq.example.validation.groups.OrderGroups;
import br.com.gnobroga.rabbitmq.example.view.OrderView;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    
    private final EntityManager entityManager;

    private final RabbitMQService rabbitMQService;

    private final ObjectMapper objectMapper;

    @Transactional
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String post(@RequestBody @Validated(OrderGroups.Create.class) final OrderEntity order) throws JsonProcessingException {
        entityManager.persist(order);

        order.getSaveProducts().forEach(product -> {
            final ProductOrderEntity productOrderEntity = ProductOrderEntity
            .builder()
            .id(new ProductOrderPK(order, product))
            .build();
            entityManager.persist(product);
            entityManager.persist(productOrderEntity);
        });

        entityManager.flush();
        entityManager.clear();
        
        rabbitMQService.send(objectMapper.writeValueAsString(order));

        final var objectWriter = objectMapper.writerWithView(OrderView.class);
        return objectWriter.writeValueAsString(entityManager.find(OrderEntity.class, order.getId()));
    }
}
