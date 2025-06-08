package com.lucasmoraist.event_hub.infra.controller.impl;

import com.lucasmoraist.event_hub.application.service.InscriptionsService;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import com.lucasmoraist.event_hub.infra.controller.InscriptionsController;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
public class InscriptionsControllerImpl implements InscriptionsController {

    @Autowired
    private InscriptionsService service;

    @Override
    public ResponseEntity<InscriptionResponse> subscribe(UUID userId, UUID eventId) {
        log.info("Subscribing user {} to event {}", userId, eventId);
        return ResponseEntity.ok().body(service.subscribe(userId, eventId));
    }

    @Override
    public ResponseEntity<InscriptionResponse> confirm(UUID inscriptionId) {
        log.info("Confirming inscription {}", inscriptionId);
        return ResponseEntity.ok().body(service.confirm(inscriptionId));
    }

    @Override
    public ResponseEntity<InscriptionResponse> cancel(UUID inscriptionId) {
        log.info("Cancelling inscription {}", inscriptionId);
        return ResponseEntity.ok().body(service.cancel(inscriptionId));
    }

    @Override
    public ResponseEntity<List<InscriptionResponse>> findByUserId(UUID userId) {
    log.info("Finding inscriptions for user {}", userId);
        return ResponseEntity.ok().body(service.findByUserId(userId));
    }

    @Override
    public ResponseEntity<List<InscriptionResponse>> findByEventId(UUID eventId) {
    log.info("Finding inscriptions for event {}", eventId);
        return ResponseEntity.ok().body(service.findByEventId(eventId));
    }

    @Override
    public ResponseEntity<List<InscriptionResponse>> listWaitingList(UUID eventId) {
        log.info("Listing waiting list for event {}", eventId);
        return ResponseEntity.ok().body(service.listWaitingList(eventId));
    }

    @Override
    public ResponseEntity<Void> checkIn(UUID inscriptionId) {
        log.info("Checking in inscription {}", inscriptionId);
        this.service.checkIn(inscriptionId);
        return ResponseEntity.noContent().build();
    }

}
