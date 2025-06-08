package com.lucasmoraist.event_hub.infra.controller.impl;

import com.lucasmoraist.event_hub.application.service.EventsService;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import com.lucasmoraist.event_hub.infra.controller.EventsController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
public class EventsControllerImpl implements EventsController {

    @Autowired
    private EventsService service;

    @Override
    public ResponseEntity<Void> createEvent(EventsRequest request) {
        log.info("Creating event with request: {}", request);
        this.service.createEvent(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<EventsResponse> findById(UUID id) {
        log.info("Finding event by ID: {}", id);
        return ResponseEntity.ok().body(this.service.findById(id));
    }

    @Override
    public ResponseEntity<Void> updateEvent(UUID id, EventsRequest request) {
        log.info("Updating event with ID: {} and request: {}", id, request);
        this.service.updateEvent(id, request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteEvent(UUID id) {
        log.info("Deleting event with ID: {}", id);
        this.service.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<EventsResponse>> findAll() {
        log.info("Listing all events");
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @Override
    public ResponseEntity<List<EventsResponse>> listUpComing() {
        log.info("Listing upcoming events");
        return ResponseEntity.ok().body(this.service.listUpComing());
    }

    @Override
    public ResponseEntity<List<EventsResponse>> listAvailableEvents() {
        log.info("Listing available events");
        return ResponseEntity.ok().body(this.service.listAvailableEvents());
    }

}
