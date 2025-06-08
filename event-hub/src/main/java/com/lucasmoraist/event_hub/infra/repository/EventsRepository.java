package com.lucasmoraist.event_hub.infra.repository;

import com.lucasmoraist.event_hub.domain.entity.Events;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventsRepository extends MongoRepository<Events, String> {

}
