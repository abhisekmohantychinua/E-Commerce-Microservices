package dev.abhisek.userservice.user.models;

import dev.abhisek.userservice.address.models.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String id;
    @Column(unique = true, length = 40)
    private String email;
    @Column(length = 10)
    private String password;
    @Column(length = 40)
    private String fullName;
    private String profile;
    @Enumerated(STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

}
