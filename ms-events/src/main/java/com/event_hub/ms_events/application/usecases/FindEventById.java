package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.domain.exception.NotFoundException;
import com.event_hub.ms_events.infra.controller.response.EventsResponse;
import com.event_hub.ms_events.infra.repository.EventsRepository;
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
