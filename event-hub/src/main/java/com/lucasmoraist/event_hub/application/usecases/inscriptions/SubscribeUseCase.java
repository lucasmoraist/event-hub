package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.application.email.EmailService;
import com.lucasmoraist.event_hub.application.usecases.events.GetEventById;
import com.lucasmoraist.event_hub.application.usecases.users.GetUserById;
import com.lucasmoraist.event_hub.application.usecases.utils.InscriptionResponseUtils;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubscribeUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final GetUserById getUserById;
    private final GetEventById getEventById;
    private final EmailService emailService;

    public InscriptionResponse execute(String userId, String eventId) {
        log.info("Subscribing user with id {} to event with id {}", userId, eventId);

        User user = this.getUserById.execute(userId);
        log.debug("User found: {}", user);
        Events event = this.getEventById.execute(eventId);
        log.debug("Event found: {}", event);

        Inscriptions inscription = new Inscriptions(user.getId(), event.getId());
        this.inscriptionRepository.save(inscription);
        log.info("User with id {} subscribed to event with id {}", userId, eventId);

        this.emailService.confirmInscription(inscription);

        return InscriptionResponseUtils.build(inscription, user, event);
    }

}
