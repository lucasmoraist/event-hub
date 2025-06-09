package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CheckInInscriptionUseCase {

    private final InscriptionsRepository inscriptionRepository;

    public void execute(String inscriptionId) {
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

    private Inscriptions getInscriptionById(String id) {
        return this.inscriptionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Inscription with id {} not found", id);
                    return new RuntimeException("Inscription not found");
                });
    }

}
