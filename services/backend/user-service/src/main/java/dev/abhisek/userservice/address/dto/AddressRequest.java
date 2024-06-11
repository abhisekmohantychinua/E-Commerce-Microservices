package dev.abhisek.userservice.address.dto;

public record AddressRequest(
        String address,
        String city,
        String zipcode,
        String type
) {
}
