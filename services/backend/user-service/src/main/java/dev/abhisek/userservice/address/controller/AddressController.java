package dev.abhisek.userservice.address.controller;

import dev.abhisek.userservice.address.dto.AddressRequest;
import dev.abhisek.userservice.address.dto.AddressResponse;
import dev.abhisek.userservice.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
            @RequestBody AddressRequest request,
            @RequestHeader(name = "user_id") String userId
    ) {
        return ResponseEntity.ok(service.createAddress(request, userId));
    }


    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddress(@RequestHeader(name = "user_id") String userId) {
        return ResponseEntity.ok(service.getAllUserAddress(userId));
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable String id, @RequestHeader(name = "user_id") String userId) {
        return ResponseEntity.ok(service.getUserAddressById(id, userId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id, @RequestHeader(name = "user_id") String userId) {
        service.deleteUserAddressById(id, userId);
        return ResponseEntity.noContent().build();
    }

}
