package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.exception.NotFoundException;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class FindEventById {

    private final EventsRepository repository;

    public EventsResponse execute(String id) {
        log.debug("Fetching event with id {}", id);
        return this.repository.findById(id)
                .map(EventsResponse::new)
                .orElseThrow(() -> {
                    log.error("Event with id {} not found", id);
                    return new NotFoundException("Event not found");
                });
    }

}
