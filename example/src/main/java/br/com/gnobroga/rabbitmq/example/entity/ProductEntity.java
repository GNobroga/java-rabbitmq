package br.com.gnobroga.rabbitmq.example.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.gnobroga.rabbitmq.example.validation.groups.OrderGroups;
import br.com.gnobroga.rabbitmq.example.view.OrderView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductEntity {
    
    @JsonView(OrderView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonView(OrderView.class)
    @NotBlank(message = "product.name is required", groups = OrderGroups.Create.class)
    private String name;

    @JsonView(OrderView.class)
    @NotNull(message = "product.price is required", groups = OrderGroups.Create.class)
    private BigDecimal price;

}
