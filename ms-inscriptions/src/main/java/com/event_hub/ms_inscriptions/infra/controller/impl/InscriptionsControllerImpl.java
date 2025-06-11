package com.event_hub.ms_inscriptions.infra.controller.impl;

import com.event_hub.ms_inscriptions.application.service.InscriptionsService;
import com.event_hub.ms_inscriptions.infra.controller.InscriptionsController;
import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class InscriptionsControllerImpl implements InscriptionsController {

    @Autowired
    private InscriptionsService service;

    @Override
    public ResponseEntity<InscriptionResponse> subscribe(String userId, String eventId) {
        log.info("Subscribing user {} to event {}", userId, eventId);
        return ResponseEntity.ok().body(service.subscribe(userId, eventId));
    }

    @Override
    public ResponseEntity<InscriptionResponse> confirm(String inscriptionId) {
        log.info("Confirming inscription {}", inscriptionId);
        return ResponseEntity.ok().body(service.confirm(inscriptionId));
    }

    @Override
    public ResponseEntity<InscriptionResponse> cancel(String inscriptionId) {
        log.info("Cancelling inscription {}", inscriptionId);
        return ResponseEntity.ok().body(service.cancel(inscriptionId));
    }

    @Override
    public ResponseEntity<List<InscriptionResponse>> findByUserId(String userId) {
    log.info("Finding inscriptions for user {}", userId);
        return ResponseEntity.ok().body(service.findByUserId(userId));
    }

    @Override
    public ResponseEntity<List<InscriptionResponse>> findByEventId(String eventId) {
    log.info("Finding inscriptions for event {}", eventId);
        return ResponseEntity.ok().body(service.findByEventId(eventId));
    }

    @Override
    public ResponseEntity<List<InscriptionResponse>> listWaitingList(String eventId) {
        log.info("Listing waiting list for event {}", eventId);
        return ResponseEntity.ok().body(service.listWaitingList(eventId));
    }

    @Override
    public ResponseEntity<Void> checkIn(String inscriptionId) {
        log.info("Checking in inscription {}", inscriptionId);
        this.service.checkIn(inscriptionId);
        return ResponseEntity.noContent().build();
    }

}
