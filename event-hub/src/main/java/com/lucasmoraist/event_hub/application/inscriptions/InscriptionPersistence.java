package com.lucasmoraist.event_hub.application.inscriptions;

import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;

import java.util.List;
import java.util.UUID;

public interface InscriptionPersistence {
    InscriptionResponse subscribe(UUID userId, UUID eventId);
    InscriptionResponse confirm(UUID inscriptionId);
    InscriptionResponse cancel(UUID inscriptionId);
    List<InscriptionResponse> findByUserId(UUID userId);
    List<InscriptionResponse> findByEventId(UUID eventId);
    List<InscriptionResponse> listWaitingList(UUID eventId);
    void checkIn(UUID inscriptionId);
    void expireAllForEvent(UUID eventId);
}
