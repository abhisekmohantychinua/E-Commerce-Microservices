package dev.abhisek.orderservice.order.dto;

public record AddressResponse(
        String id,
        String address,
        String city,
        String zipcode,
        String type
) {
}
