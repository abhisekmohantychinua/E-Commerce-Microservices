package dev.abhisek.userservice.address.dto;

public record AddressResponse(
        String id,
        String address,
        String city,
        String zipcode,
        String type,
        String user_id
) {
}
