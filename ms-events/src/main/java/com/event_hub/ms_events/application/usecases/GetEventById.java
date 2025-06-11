package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.domain.entity.Events;
import com.event_hub.ms_events.domain.exception.NotFoundException;
import com.event_hub.ms_events.infra.repository.EventsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GetEventById {

    @Autowired
    private EventsRepository eventsRepository;

    public Events execute(String eventId) {
        return this.eventsRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Event with id {} not found", eventId);
                    return new NotFoundException("Event not found");
                });
    }

}
