package com.lucasmoraist.event_hub.application.usecases.inscriptions;

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

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class FindInscriptionByUserIdUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;

    public List<InscriptionResponse> execute(String userId) {
        log.info("Finding inscriptions for user with id {}", userId);
        return this.inscriptionRepository.findByUserId(userId).stream()
                .map(i -> {
                    User user = this.getUserById(userId);
                    Events event = this.getEventById(i.getEventId());
                    return this.buildInscriptionResponse(i, user, event);
                })
                .toList();
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
