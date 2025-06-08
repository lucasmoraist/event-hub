package com.lucasmoraist.event_hub.application.service.impl;

import com.lucasmoraist.event_hub.application.inscriptions.InscriptionPersistence;
import com.lucasmoraist.event_hub.application.service.InscriptionsService;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class InscriptionsServiceImpl implements InscriptionsService {

    private final InscriptionPersistence inscriptionPersistence;

    @Override
    public InscriptionResponse subscribe(UUID userId, UUID eventId) {
        return this.inscriptionPersistence.subscribe(userId, eventId);
    }

    @Override
    public InscriptionResponse confirm(UUID inscriptionId) {
        return this.inscriptionPersistence.confirm(inscriptionId);
    }

    @Override
    public InscriptionResponse cancel(UUID inscriptionId) {
        return this.inscriptionPersistence.cancel(inscriptionId);
    }

    @Override
    public List<InscriptionResponse> findByUserId(UUID userId) {
        return this.inscriptionPersistence.findByUserId(userId);
    }

    @Override
    public List<InscriptionResponse> findByEventId(UUID eventId) {
        return this.inscriptionPersistence.findByEventId(eventId);
    }

    @Override
    public List<InscriptionResponse> listWaitingList(UUID eventId) {
        return this.inscriptionPersistence.listWaitingList(eventId);
    }

    @Override
    public void checkIn(UUID inscriptionId) {
        this.inscriptionPersistence.checkIn(inscriptionId);
    }

    @Override
    public void expireAllForEvent(UUID eventId) {
        this.inscriptionPersistence.expireAllForEvent(eventId);
    }

}
