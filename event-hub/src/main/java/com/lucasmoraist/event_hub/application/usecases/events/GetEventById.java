package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.exception.NotFoundException;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
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
