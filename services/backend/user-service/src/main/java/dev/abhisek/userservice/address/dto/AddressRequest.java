package dev.abhisek.userservice.address.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotNull(message = "Address is a required field.")
        @NotEmpty(message = "Address is a required field.")
        String address,
        @NotNull(message = "City is a required field.")
        @NotEmpty(message = "City is a required field.")
        String city,
        @NotNull(message = "Zipcode is a required field.")
        @NotEmpty(message = "Zipcode is a required field.")
        @Size(max = 6, min = 6, message = "Zip code must be 6 digit.")
        String zipcode,
        @NotNull(message = "Zipcode is a required field.")
        @NotEmpty(message = "Zipcode is a required field.")
        @Pattern(regexp = "^(WORK|HOME|SCHOOL)$", message = "The type must be one of WORK, HOME, SCHOOL.")
        String type
) {
}
