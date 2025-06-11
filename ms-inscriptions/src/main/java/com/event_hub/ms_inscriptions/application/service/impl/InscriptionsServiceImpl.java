package com.event_hub.ms_inscriptions.application.service.impl;

import com.event_hub.ms_inscriptions.application.service.InscriptionsService;
import com.event_hub.ms_inscriptions.application.usecases.CancelInscriptionUseCase;
import com.event_hub.ms_inscriptions.application.usecases.CheckInInscriptionUseCase;
import com.event_hub.ms_inscriptions.application.usecases.ConfirmInscriptionUseCase;
import com.event_hub.ms_inscriptions.application.usecases.FindInscriptionByEventIdUseCase;
import com.event_hub.ms_inscriptions.application.usecases.FindInscriptionByUserIdUseCase;
import com.event_hub.ms_inscriptions.application.usecases.ListWaitingListUseCase;
import com.event_hub.ms_inscriptions.application.usecases.SubscribeUseCase;
import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;
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

}
