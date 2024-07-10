package dev.abhisek.orderservice.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderLine {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    private String productId;
    private Integer quantity;
    @ManyToOne
    private Order order;
}
