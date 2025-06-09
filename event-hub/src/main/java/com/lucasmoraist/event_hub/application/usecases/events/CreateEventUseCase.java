package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CreateEventUseCase {

    private final EventsRepository repository;
    private final MongoTemplate mongoTemplate;

    public void execute(EventsRequest request) {
        Events events = new Events(request);
        this.validateEvent(request);
        this.repository.save(events);
        log.debug("Event with name {} created successfully", request.title());
    }

    private void validateEvent(EventsRequest request) {
        log.debug("Validating event with name {}, date {}, time {}, location {}",
                  request.title(), request.date(), request.time(), request.location());
        Criteria criteria = Criteria
                .where("date").is(request.date())
                .and("time").is(request.time())
                .and("location").is(request.location())
                .and("title").is(request.title());

        Query query = new Query(criteria);

        long count = this.mongoTemplate.count(query, Events.class);
        log.debug("Count of events with the same name, date, time and location: {}", count);

        if (count > 0) {
            log.error("Event with name {} already exists at the same date, time and location", request.title());
            throw new IllegalArgumentException("Event with the same name, date, time and location already exists");
        }
    }

}
