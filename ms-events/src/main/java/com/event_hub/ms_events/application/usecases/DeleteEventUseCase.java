package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.domain.entity.Events;
import com.event_hub.ms_events.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeleteEventUseCase {

    private final EventsRepository repository;
    private final GetEventById getEventById;

    public void execute(String id) {
        log.debug("Deleting event with id {}", id);
        Events events = this.getEventById.execute(id);
        this.repository.delete(events);
        log.info("Event with id {} deleted successfully", id);
    }

}
