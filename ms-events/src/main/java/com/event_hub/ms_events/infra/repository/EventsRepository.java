package com.event_hub.ms_events.infra.repository;

import com.event_hub.ms_events.domain.entity.Events;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventsRepository extends MongoRepository<Events, String> {

}
