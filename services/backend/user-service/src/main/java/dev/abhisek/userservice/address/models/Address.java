package dev.abhisek.userservice.address.models;

import dev.abhisek.userservice.user.models.User;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    private String id;
    private String address;
    private String city;
    @Column(length = 6)
    private String zipcode;
    @Enumerated(STRING)
    private AddressType type;
    @ManyToOne(fetch = LAZY)
    private User user;
}
