package dev.abhisek.orderservice.carts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    private String productId;
    @Column(columnDefinition = "UNSIGNED INT(11)")
    private Integer quantity;
    private String userId;
}
