package com.lucasmoraist.event_hub.application.service;

import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;

import java.util.List;
import java.util.UUID;

public interface InscriptionsService {
    InscriptionResponse subscribe(UUID userId, UUID eventId);
    InscriptionResponse confirm(UUID inscriptionId);
    InscriptionResponse cancel(UUID inscriptionId);
    List<InscriptionResponse> findByUserId(UUID userId);
    List<InscriptionResponse> findByEventId(UUID eventId);
    List<InscriptionResponse> listWaitingList(UUID eventId);
    void checkIn(UUID inscriptionId);
    // TODO: Adicionar um Schedule para verificar as inscrições expiradas a cada 1 Hora
    void expireAllForEvent(UUID eventId);
}
