package com.lucasmoraist.event_hub.infra.repository;

import com.lucasmoraist.event_hub.domain.entity.Events;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EventsRepository extends MongoRepository<Events, UUID> {

}
