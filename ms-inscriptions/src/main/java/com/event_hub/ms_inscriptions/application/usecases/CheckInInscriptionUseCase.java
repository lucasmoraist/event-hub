package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.enums.StatusInscriptions;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
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
        log.debug("Inscription found: {}", inscription);

        inscription.checkIn(inscription.getStatus());
        this.inscriptionRepository.save(inscription);

        log.info("Inscription with id {} checked in successfully", inscriptionId);
    }

}
