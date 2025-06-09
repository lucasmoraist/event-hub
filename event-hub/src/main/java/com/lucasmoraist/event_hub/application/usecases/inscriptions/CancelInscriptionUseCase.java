package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.application.usecases.events.GetEventById;
import com.lucasmoraist.event_hub.application.usecases.users.GetUserById;
import com.lucasmoraist.event_hub.application.utils.InscriptionResponseUtils;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CancelInscriptionUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final GetUserById getUserById;
    private final GetInscriptionById getInscriptionById;
    private final GetEventById getEventById;

    public InscriptionResponse execute(String inscriptionId) {
        log.info("Cancelling inscription with id {}", inscriptionId);

        Inscriptions inscription = this.getInscriptionById.execute(inscriptionId);
        inscription.setStatus(StatusInscriptions.CANCELLED);

        User user = this.getUserById.execute(inscription.getUserId());
        log.debug("User found: {}", user);
        Events event = this.getEventById.execute(inscription.getEventId());
        log.debug("Event found: {}", event);

        this.inscriptionRepository.save(inscription);

        return InscriptionResponseUtils.build(inscription, user, event);
    }

}
