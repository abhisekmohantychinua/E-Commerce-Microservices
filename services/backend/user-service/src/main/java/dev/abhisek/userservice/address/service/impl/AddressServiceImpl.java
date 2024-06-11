package dev.abhisek.userservice.address.service.impl;

import dev.abhisek.userservice.address.dto.AddressRequest;
import dev.abhisek.userservice.address.dto.AddressResponse;
import dev.abhisek.userservice.address.mapper.AddressMapper;
import dev.abhisek.userservice.address.models.Address;
import dev.abhisek.userservice.address.models.AddressType;
import dev.abhisek.userservice.address.repo.AddressRepository;
import dev.abhisek.userservice.address.service.AddressService;
import dev.abhisek.userservice.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository repository;
    private final AddressMapper mapper;

    @Override
    public AddressResponse createAddress(AddressRequest request, String userId) {
        Address address = Address.builder()
                .id(UUID.randomUUID().toString())
                .address(request.address())
                .city(request.city())
                .zipcode(request.zipcode())
                .type(AddressType.valueOf(request.type()))
                .user(User.builder().id(userId).build())
                .build();
        address = repository.save(address);
        return mapper.toAddressResponse(address);
    }

    @Override
    public List<AddressResponse> getAllUserAddress(String userId) {
        return repository.findAllByUser(User.builder().id(userId).build())
                .stream().map(mapper::toAddressResponse)
                .toList();
    }

    @Override
    public AddressResponse getUserAddressById(String id, String userId) {
        return repository.findAddressByIdAndUser(id, User.builder().id(userId).build())
                .map(mapper::toAddressResponse)
                .orElseThrow();// todo- exception
    }

    @Override
    public void deleteUserAddressById(String id, String userId) {
        Address address = repository.findAddressByIdAndUser(id, User.builder().id(userId).build())
                .orElseThrow();// todo- exception

        repository.delete(address);
    }


}
