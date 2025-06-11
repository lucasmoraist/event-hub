package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.enums.StatusInscriptions;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExpireAllForEventUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final EventsService eventsService;

    @Scheduled(fixedRate = 300000) // 5 min
    public void execute() {
        List<EventsData> events = this.eventsService.getAllEvents();

        for (EventsData event : events) {
            if (event.date() == null || event.time() == null) {
                log.warn("Event with id {} has missing date or time", event.id());
                continue;
            }

            LocalDate parseDate = LocalDate.parse(event.date());
            LocalTime parseTime = LocalTime.parse(event.time());
            LocalDateTime eventDateTime = LocalDateTime.of(parseDate, parseTime);

            if (eventDateTime.isBefore(LocalDateTime.now())) {
                log.info("Expiring all inscriptions for event with id {}", event.id());
                List<Inscriptions> inscriptions = this.inscriptionRepository.findByEventId(event.id());

                for (Inscriptions i : inscriptions) {
                    if (StatusInscriptions.PENDING.equals(i.getStatus()) || StatusInscriptions.CONFIRMED.equals(i.getStatus())) {
                        i.setStatus(StatusInscriptions.EXPIRED);
                        this.inscriptionRepository.save(i);
                        log.info("Inscription with id {} expired", i.getId());
                    }
                }
            } else {
                log.info("Event with id {} is not expired yet", event.id());
            }
        }
    }

}
