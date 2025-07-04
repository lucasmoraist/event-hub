package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.application.service.UserService;
import com.event_hub.ms_inscriptions.application.utils.InscriptionResponseUtils;
import com.event_hub.ms_inscriptions.domain.enums.StatusInscriptions;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListWaitingListUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final UserService userService;
    private final EventsService eventsService;

    public List<InscriptionResponse> execute(String eventId) {
        log.info("Listing waiting list for event with id {}", eventId);
        return this.inscriptionRepository.findByEventId(eventId).stream()
                .filter(i -> i.getStatus() == StatusInscriptions.PENDING)
                .map(i -> {
                    UserData user = this.userService.getUserById(i.getUserId());
                    EventsData event = this.eventsService.getEventById(eventId);
                    return InscriptionResponseUtils.build(i, user, event);
                })
                .toList();
    }

}
