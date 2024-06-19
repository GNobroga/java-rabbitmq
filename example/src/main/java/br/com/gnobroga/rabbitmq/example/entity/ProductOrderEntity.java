package br.com.gnobroga.rabbitmq.example.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "product_order")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductOrderEntity {

    @EmbeddedId
    private ProductOrderPK id;
    
    private boolean paid;

    public ProductEntity getProduct() {
        return id.getProduct();
    }
}
