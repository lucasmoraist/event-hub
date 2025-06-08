package com.lucasmoraist.event_hub.application.inscriptions.impl;

import com.lucasmoraist.event_hub.application.inscriptions.InscriptionPersistence;
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

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class InscriptionPersistenceImpl implements InscriptionPersistence {

    private final InscriptionsRepository inscriptionRepository;
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;

    @Override
    public InscriptionResponse subscribe(String userId, String eventId) {
        log.info("Subscribing user with id {} to event with id {}", userId, eventId);

        User user = this.getUserById(userId);
        log.debug("User found: {}", user);
        Events event = this.getEventById(eventId);
        log.debug("Event found: {}", event);

        Inscriptions events = new Inscriptions(user.getId(), event.getId());
        this.inscriptionRepository.save(events);
        log.info("User with id {} subscribed to event with id {}", userId, eventId);

        return this.buildInscriptionResponse(events, user, event);
    }

    @Override
    public InscriptionResponse confirm(String inscriptionId) {
        log.info("Confirming inscription with id {}", inscriptionId);

        Inscriptions inscription = this.getInscriptionById(inscriptionId);
        inscription.setStatus(StatusInscriptions.CONFIRMED);

        User user = this.getUserById(inscription.getUserId());
        log.debug("User found: {}", user);
        Events event = this.getEventById(inscription.getEventId());
        log.debug("Event found: {}", event);

        return this.buildInscriptionResponse(inscription, user, event);
    }

    @Override
    public InscriptionResponse cancel(String inscriptionId) {
        log.info("Cancelling inscription with id {}", inscriptionId);

        Inscriptions inscription = this.getInscriptionById(inscriptionId);
        inscription.setStatus(StatusInscriptions.CANCELLED);

        User user = this.getUserById(inscription.getUserId());
        log.debug("User found: {}", user);
        Events event = this.getEventById(inscription.getEventId());
        log.debug("Event found: {}", event);

        return this.buildInscriptionResponse(inscription, user, event);
    }

    @Override
    public List<InscriptionResponse> findByUserId(String userId) {
        log.info("Finding inscriptions for user with id {}", userId);
        return this.inscriptionRepository.findByUserId(userId).stream()
                .map(i -> {
                    User user = this.getUserById(userId);
                    Events event = this.getEventById(i.getEventId());
                    return this.buildInscriptionResponse(i, user, event);
                })
                .toList();
    }

    @Override
    public List<InscriptionResponse> findByEventId(String eventId) {
        log.info("Finding inscriptions for event with id {}", eventId);
        return this.inscriptionRepository.findByEventId(eventId).stream()
                .map(i -> {
                    User user = this.getUserById(i.getUserId());
                    Events event = this.getEventById(eventId);
                    return this.buildInscriptionResponse(i, user, event);
                })
                .toList();
    }

    @Override
    public List<InscriptionResponse> listWaitingList(String eventId) {
        log.info("Listing waiting list for event with id {}", eventId);
        return this.inscriptionRepository.findByEventId(eventId).stream()
                .filter(i -> i.getStatus() == StatusInscriptions.PENDING)
                .map(i -> {
                    User user = this.getUserById(i.getUserId());
                    Events event = this.getEventById(eventId);
                    return this.buildInscriptionResponse(i, user, event);
                })
                .toList();
    }

    @Override
    public void checkIn(String inscriptionId) {
        log.info("Checking in inscription with id {}", inscriptionId);
        Inscriptions inscription = this.getInscriptionById(inscriptionId);

        if (inscription.getStatus() != StatusInscriptions.CONFIRMED) {
            log.error("Inscription with id {} is not confirmed", inscriptionId);
            throw new RuntimeException("Inscription is not confirmed");
        }

        inscription.setStatus(StatusInscriptions.CHECKED_IN);
        this.inscriptionRepository.save(inscription);
        log.info("Inscription with id {} checked in successfully", inscriptionId);
    }

    @Override
    public void expireAllForEvent(String eventId) {
        log.info("Expiring all inscriptions for event with id {}", eventId);
        List<Inscriptions> inscriptions = this.inscriptionRepository.findByEventId(eventId);

        inscriptions.forEach(i -> {
            if (i.getStatus() == StatusInscriptions.PENDING) {
                i.setStatus(StatusInscriptions.EXPIRED);
                this.inscriptionRepository.save(i);
                log.info("Inscription with id {} expired", i.getId());
            }
        });
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
