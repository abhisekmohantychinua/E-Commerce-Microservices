package dev.abhisek.userservice.address.mapper;

import dev.abhisek.userservice.address.dto.AddressResponse;
import dev.abhisek.userservice.address.models.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {

    public AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getAddress(),
                address.getCity(),
                address.getZipcode(),
                address.getType().name(),
                address.getUser().getId()
        );
    }
}

