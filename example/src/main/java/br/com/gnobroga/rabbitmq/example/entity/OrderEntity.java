package br.com.gnobroga.rabbitmq.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.gnobroga.rabbitmq.example.validation.groups.OrderGroups;
import br.com.gnobroga.rabbitmq.example.view.OrderView;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "orders")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderEntity {

    @JsonView(OrderView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @JsonView(OrderView.class)
    @NotNull(message = "order.quantity is required", groups = OrderGroups.Create.class)
    private Long quantity;

    @Transient
    @NotNull(message = "order.product is required", groups = OrderGroups.Create.class)
    @Valid
    private List<ProductEntity> saveProducts;

    @JsonIgnore
    @OneToMany(mappedBy = "id.order", fetch = FetchType.EAGER)
    private List<ProductOrderEntity> productOrders;

    @JsonView(OrderView.class)
    public List<ProductEntity> getProducts() {
        if (Objects.isNull(productOrders)) return new ArrayList<>();
        return productOrders.stream().map(obj -> obj.getProduct()).toList();
    }
}
