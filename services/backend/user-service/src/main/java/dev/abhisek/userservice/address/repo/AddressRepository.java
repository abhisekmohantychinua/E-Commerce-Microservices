package dev.abhisek.userservice.address.repo;

import dev.abhisek.userservice.address.models.Address;
import dev.abhisek.userservice.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findAllByUser(User user);

    Optional<Address> findAddressByIdAndUser(String id, User user);
}
