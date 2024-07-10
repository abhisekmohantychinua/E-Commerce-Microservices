package dev.abhisek.orderservice.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private Double totalAmount;
    @Enumerated(STRING)
    private Status status;
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;
    private String userId;
    private String addressId;
    private String paymentId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", fetch = EAGER, cascade = ALL)
    private List<OrderLine> orderLines;
}
