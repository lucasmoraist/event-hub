package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ExpireAllForEventUseCase {

    private final InscriptionsRepository inscriptionRepository;

    @Scheduled(fixedRate = 60000) // 60 seconds
    public void execute(String eventId) {
        log.info("Expiring all inscriptions for event with id {}", eventId);
        List<Inscriptions> inscriptions = this.inscriptionRepository.findByEventId(eventId);

        for (Inscriptions i : inscriptions) {
            if (StatusInscriptions.PENDING.equals(i.getStatus())) {
                i.setStatus(StatusInscriptions.EXPIRED);
                this.inscriptionRepository.save(i);
                log.info("Inscription with id {} expired", i.getId());
            }
        }
    }

}
