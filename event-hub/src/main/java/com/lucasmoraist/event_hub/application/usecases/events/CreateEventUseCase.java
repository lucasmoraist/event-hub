package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CreateEventUseCase {

    private final EventsRepository repository;

    public void execute(EventsRequest request) {
        Events events = new Events(request);
        this.repository.save(events);
        log.debug("Event with name {} created successfully", request.title());
    }

}
