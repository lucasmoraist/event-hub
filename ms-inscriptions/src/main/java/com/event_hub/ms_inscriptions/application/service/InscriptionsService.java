package com.event_hub.ms_inscriptions.application.service;

import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;

import java.util.List;

public interface InscriptionsService {
    InscriptionResponse subscribe(String userId, String eventId);
    InscriptionResponse confirm(String inscriptionId);
    InscriptionResponse cancel(String inscriptionId);
    List<InscriptionResponse> findByUserId(String userId);
    List<InscriptionResponse> findByEventId(String eventId);
    List<InscriptionResponse> listWaitingList(String eventId);
    void checkIn(String inscriptionId);
}
