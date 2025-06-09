package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.domain.exception.StatusException;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CheckInInscriptionUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final GetInscriptionById getInscriptionById;

    public void execute(String inscriptionId) {
        log.info("Checking in inscription with id {}", inscriptionId);
        Inscriptions inscription = this.getInscriptionById.execute(inscriptionId);

        this.verifyStatus(inscription);

        inscription.setStatus(StatusInscriptions.CHECKED_IN);
        this.inscriptionRepository.save(inscription);
        log.info("Inscription with id {} checked in successfully", inscriptionId);
    }

    private void verifyStatus(Inscriptions inscription) {
        if (inscription.getStatus() != StatusInscriptions.CONFIRMED) {
            log.error("Inscription with id {} is not confirmed", inscription.getId());
            throw new StatusException("Inscription is not confirmed");
        }
    }

}
