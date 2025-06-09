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
public class UpdateEventUseCase {

    private final EventsRepository repository;

    public void execute(String id, EventsRequest request) {
        log.debug("Updating event with id {}", id);
        Events events = this.getEventById(id);

        events.updateEvent(request);
        this.repository.save(events);
    }

    private Events getEventById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id {} not found", id);
                    return new RuntimeException("Event not found");
                });
    }

}
