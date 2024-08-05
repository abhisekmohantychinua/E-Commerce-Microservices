package dev.abhisek.userservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public record UserRequest(
        @Email(message = "Provide a valid email. Ex. example@email.com")
        @NotEmpty(message = "Email is a required field.")
        @NotNull(message = "Email is a required field")
        String email,
        @NotEmpty(message = "Password is a required field.")
        @NotNull(message = "Password is a required field")
        String password,
        @NotEmpty(message = "Full name is a required field.")
        @NotNull(message = "Full name is a required field")
        String fullName,
        @Pattern(regexp = "^(USER|ADMIN|DEV)$", message = "The role must be one of USER, ADMIN, DEV.")
        String role,
        @NotNull(message = "Profile picture is a required field")
        MultipartFile profile
) {
}
