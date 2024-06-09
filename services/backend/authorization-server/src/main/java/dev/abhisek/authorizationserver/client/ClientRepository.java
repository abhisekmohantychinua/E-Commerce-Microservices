package dev.abhisek.authorizationserver.client;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findClientByClientId(String clientId);
}
