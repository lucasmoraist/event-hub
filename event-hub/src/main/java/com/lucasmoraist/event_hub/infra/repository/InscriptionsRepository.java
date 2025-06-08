package com.lucasmoraist.event_hub.infra.repository;

import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InscriptionsRepository extends MongoRepository<Inscriptions, String> {
    List<Inscriptions> findByUserId(String userId);
    List<Inscriptions> findByEventId(String eventId);
}
