package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.application.email.EmailService;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubscribeUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final EmailService emailService;

    public InscriptionResponse execute(String userId, String eventId) {
        log.info("Subscribing user with id {} to event with id {}", userId, eventId);

        User user = this.getUserById(userId);
        log.debug("User found: {}", user);
        Events event = this.getEventById(eventId);
        log.debug("Event found: {}", event);

        Inscriptions events = new Inscriptions(user.getId(), event.getId());
        this.inscriptionRepository.save(events);
        log.info("User with id {} subscribed to event with id {}", userId, eventId);

        this.emailService.confirmInscription(events);

        return this.buildInscriptionResponse(events, user, event);
    }

    private InscriptionResponse buildInscriptionResponse(Inscriptions inscription, User user, Events event) {
        return InscriptionResponse.builder()
                .id(inscription.getId())
                .userName(user.getName())
                .userEmail(user.getEmail())
                .eventTitle(event.getTitle())
                .eventDate(event.getDate().toString())
                .eventTime(event.getTime().toString())
                .eventLocation(event.getLocation())
                .status(inscription.getStatus().name())
                .build();
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", userId);
                    return new RuntimeException("User not found");
                });
    }

    private Events getEventById(String eventId) {
        return this.eventsRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Event with id {} not found", eventId);
                    return new RuntimeException("Event not found");
                });
    }

}
