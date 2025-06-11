package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.application.service.UserService;
import com.event_hub.ms_inscriptions.application.utils.InscriptionResponseUtils;
import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.model.ConfirmInscriptionData;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;
import com.event_hub.ms_inscriptions.infra.producer.InscriptionProducer;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static org.springframework.integration.support.MessageBuilder.withPayload;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubscribeUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final UserService userService;
    private final EventsService eventsService;
    private final InscriptionProducer producer;

    public InscriptionResponse execute(String userId, String eventId) {
        log.info("Subscribing user with id {} to event with id {}", userId, eventId);

        UserData user = this.userService.getUserById(userId);
        log.debug("User found: {}", user);
        EventsData event = this.eventsService.getEventById(eventId);
        log.debug("Event found: {}", event);

        Inscriptions inscription = new Inscriptions(user.id(), event.id());
        this.inscriptionRepository.save(inscription);
        log.info("User with id {} subscribed to event with id {}", userId, eventId);

        this.sendMessage(user, event, inscription);

        return InscriptionResponseUtils.build(inscription, user, event);
    }

    private void sendMessage(UserData user, EventsData events, Inscriptions inscriptions) {
        ConfirmInscriptionData data = new ConfirmInscriptionData(
                inscriptions.getId(),
                user.name(),
                user.email(),
                events.title(),
                events.date(),
                events.time(),
                events.location()
        );

        producer.sendMessage(withPayload(data).build());
    }

}
