package com.lucasmoraist.event_hub.application.events.impl;

import com.lucasmoraist.event_hub.application.events.EventsPersistence;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class EventsPersistenceImpl implements EventsPersistence {

    private final EventsRepository repository;

    @Override
    public void createEvent(EventsRequest request) {
        Events events = new Events(request);
        this.repository.save(events);
        log.debug("Event with name {} created successfully", request.title());
    }

    @Override
    public EventsResponse findById(String id) {
        log.debug("Fetching event with id {}", id);
        return this.repository.findById(id)
                .map(EventsResponse::new)
                .orElseThrow(() -> {
                    log.error("Event with id {} not found", id);
                    return new RuntimeException("Event not found");
                });
    }

    @Override
    public void updateEvent(String id, EventsRequest request) {
        log.debug("Updating event with id {}", id);
        Events events = this.getEventById(id);

        events.updateEvent(request);
        this.repository.save(events);
    }

    @Override
    public void deleteEvent(String id) {
        log.debug("Deleting event with id {}", id);
        Events events = this.getEventById(id);
        this.repository.delete(events);
        log.info("Event with id {} deleted successfully", id);
    }

    @Override
    public List<EventsResponse> findAll() {
        log.info("Fetching all events");
        return this.repository.findAll()
                .stream()
                .map(EventsResponse::new)
                .toList();
    }

    @Override
    public List<EventsResponse> listUpComing() {
        log.info("Fetching all upcoming events");
        return this.repository.findAll()
                .stream()
                .filter(e -> e.getDate().isAfter(LocalDate.now()))
                .map(EventsResponse::new)
                .toList();
    }

    @Override
    public List<EventsResponse> listAvailableEvents() {
        log.info("Fetching all available events");
        return this.repository.findAll()
                .stream()
                .filter(e -> e.getCapacity() > 0)
                .map(EventsResponse::new)
                .toList();
    }

    private Events getEventById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event with id {} not found", id);
                    return new RuntimeException("Event not found");
                });
    }

}
