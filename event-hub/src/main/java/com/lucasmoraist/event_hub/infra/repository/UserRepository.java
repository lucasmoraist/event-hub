package com.lucasmoraist.event_hub.infra.repository;

import com.lucasmoraist.event_hub.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
