package com.lucasmoraist.event_hub.infra.service;

import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;

import java.util.List;

public interface InscriptionsService {
    InscriptionResponse subscribe(String userId, String eventId);
    InscriptionResponse confirm(String inscriptionId);
    InscriptionResponse cancel(String inscriptionId);
    List<InscriptionResponse> findByUserId(String userId);
    List<InscriptionResponse> findByEventId(String eventId);
    List<InscriptionResponse> listWaitingList(String eventId);
    void checkIn(String inscriptionId);
    void expireAllForEvent(String eventId);
}
