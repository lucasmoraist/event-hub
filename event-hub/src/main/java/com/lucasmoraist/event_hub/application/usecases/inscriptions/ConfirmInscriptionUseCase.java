package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
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
public class ConfirmInscriptionUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;

    public InscriptionResponse execute(String inscriptionId) {
        log.info("Confirming inscription with id {}", inscriptionId);

        Inscriptions inscription = this.getInscriptionById(inscriptionId);
        inscription.setStatus(StatusInscriptions.CONFIRMED);

        User user = this.getUserById(inscription.getUserId());
        log.debug("User found: {}", user);
        Events event = this.getEventById(inscription.getEventId());
        log.debug("Event found: {}", event);

        return this.buildInscriptionResponse(inscription, user, event);
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

    private Inscriptions getInscriptionById(String id) {
        return this.inscriptionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Inscription with id {} not found", id);
                    return new RuntimeException("Inscription not found");
                });
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
