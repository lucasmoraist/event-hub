package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExpireAllForEventUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final EventsRepository eventsRepository;

    @Scheduled(fixedRate = 300000) // 5 min
    public void execute() {
        List<Events> events = this.eventsRepository.findAll();

        for (Events event : events) {
            if (event.getDate() == null || event.getTime() == null) {
                log.warn("Event with id {} has missing date or time", event.getId());
                continue;
            }

            LocalDateTime eventDateTime = LocalDateTime.of(event.getDate(), event.getTime());

            if (eventDateTime.isBefore(LocalDateTime.now())) {
                log.info("Expiring all inscriptions for event with id {}", event.getId());
                List<Inscriptions> inscriptions = this.inscriptionRepository.findByEventId(event.getId());

                for (Inscriptions i : inscriptions) {
                    if (StatusInscriptions.PENDING.equals(i.getStatus())) {
                        i.setStatus(StatusInscriptions.EXPIRED);
                        this.inscriptionRepository.save(i);
                        log.info("Inscription with id {} expired", i.getId());
                    }
                }
            } else {
                log.info("Event with id {} is not expired yet", event.getId());
            }
        }
    }

}
