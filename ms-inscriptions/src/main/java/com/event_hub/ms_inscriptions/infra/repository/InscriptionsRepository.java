package com.event_hub.ms_inscriptions.infra.repository;

import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InscriptionsRepository extends MongoRepository<Inscriptions, String> {
    List<Inscriptions> findByUserId(String userId);
    List<Inscriptions> findByEventId(String eventId);
}
