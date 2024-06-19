package br.com.gnobroga.rabbitmq.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductOrderPK {
    
    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn
    private OrderEntity order; 

    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductEntity product;

}
