package dev.abhisek.userservice.address.service;

import dev.abhisek.userservice.address.dto.AddressRequest;
import dev.abhisek.userservice.address.dto.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse createAddress(AddressRequest request, String userId);

    List<AddressResponse> getAllUserAddress(String userId);

    AddressResponse getUserAddressById(String id, String userId);

    void deleteUserAddressById(String id, String userId);
}
