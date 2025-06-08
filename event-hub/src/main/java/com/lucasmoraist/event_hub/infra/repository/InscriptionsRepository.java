package com.lucasmoraist.event_hub.infra.repository;

import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface InscriptionsRepository extends MongoRepository<Inscriptions, UUID> {
    List<Inscriptions> findByUserId(UUID userId);
    List<Inscriptions> findByEventId(UUID eventId);
}
