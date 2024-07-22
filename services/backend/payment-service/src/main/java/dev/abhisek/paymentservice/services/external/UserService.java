package dev.abhisek.paymentservice.services.external;

import dev.abhisek.paymentservice.dto.UserProfileResponse;
import dev.abhisek.paymentservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${services.urls.user}")
public interface UserService {

    @GetMapping("auth")
    UserResponse getUserByUsername(@RequestParam String username);

    @GetMapping("profile")
    UserProfileResponse getUserProfile(@RequestHeader("user_id") String id);
}
