package dev.abhisek.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    private String id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(fetch = FetchType.EAGER, cascade = ALL, orphanRemoval = true)
    private List<Detail> details = new ArrayList<>();
    @OneToMany(cascade = ALL, orphanRemoval = true)
    private List<Picture> pictures = new ArrayList<>();
    private Double price;
    private Integer quantity;
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedAt;
    private Boolean notHidden = true;
}
