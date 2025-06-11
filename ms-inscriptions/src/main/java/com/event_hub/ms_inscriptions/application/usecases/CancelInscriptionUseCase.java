package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.application.service.UserService;
import com.event_hub.ms_inscriptions.application.utils.InscriptionResponseUtils;
import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CancelInscriptionUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final GetInscriptionById getInscriptionById;
    private final UserService userService;
    private final EventsService eventsService;

    public InscriptionResponse execute(String inscriptionId) {
        log.info("Cancelling inscription with id {}", inscriptionId);

        Inscriptions inscription = this.getInscriptionById.execute(inscriptionId);
        log.debug("Inscription found: {}", inscription);

        UserData user = this.userService.getUserById(inscription.getUserId());
        log.debug("User found: {}", user);

        EventsData event = this.eventsService.getEventById(inscription.getEventId());
        log.debug("Event found: {}", event);

        inscription.cancel(inscription.getStatus());
        this.inscriptionRepository.save(inscription);

        return InscriptionResponseUtils.build(inscription, user, event);
    }

}
