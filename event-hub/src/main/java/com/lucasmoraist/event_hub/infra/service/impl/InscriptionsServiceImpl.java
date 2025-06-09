package com.lucasmoraist.event_hub.infra.service.impl;

import com.lucasmoraist.event_hub.infra.service.InscriptionsService;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.CancelInscriptionUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.CheckInInscriptionUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.ConfirmInscriptionUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.ExpireAllForEventUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.FindInscriptionByEventIdUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.FindInscriptionByUserIdUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.ListWaitingListUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.SubscribeUseCase;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class InscriptionsServiceImpl implements InscriptionsService {

    private final CancelInscriptionUseCase cancelInscriptionUseCase;
    private final CheckInInscriptionUseCase checkInInscriptionUseCase;
    private final ConfirmInscriptionUseCase confirmInscriptionUseCase;
    private final ExpireAllForEventUseCase expireAllForEventUseCase;
    private final FindInscriptionByEventIdUseCase findInscriptionByEventIdUseCase;
    private final FindInscriptionByUserIdUseCase findInscriptionByUserIdUseCase;
    private final ListWaitingListUseCase listWaitingListUseCase;
    private final SubscribeUseCase subscribeUseCase;

    @Override
    public InscriptionResponse subscribe(String userId, String eventId) {
        return this.subscribeUseCase.execute(userId, eventId);
    }

    @Override
    public InscriptionResponse confirm(String inscriptionId) {
        return this.confirmInscriptionUseCase.execute(inscriptionId);
    }

    @Override
    public InscriptionResponse cancel(String inscriptionId) {
        return this.cancelInscriptionUseCase.execute(inscriptionId);
    }

    @Override
    public List<InscriptionResponse> findByUserId(String userId) {
        return this.findInscriptionByUserIdUseCase.execute(userId);
    }

    @Override
    public List<InscriptionResponse> findByEventId(String eventId) {
        return this.findInscriptionByEventIdUseCase.execute(eventId);
    }

    @Override
    public List<InscriptionResponse> listWaitingList(String eventId) {
        return this.listWaitingListUseCase.execute(eventId);
    }

    @Override
    public void checkIn(String inscriptionId) {
        this.checkInInscriptionUseCase.execute(inscriptionId);
    }

    @Override
    public void expireAllForEvent(String eventId) {
        this.expireAllForEventUseCase.execute(eventId);
    }

}
