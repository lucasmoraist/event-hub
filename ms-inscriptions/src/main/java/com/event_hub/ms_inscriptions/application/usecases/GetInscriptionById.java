package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.exception.NotFoundException;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GetInscriptionById {

    @Autowired
    private InscriptionsRepository inscriptionRepository;

    public Inscriptions execute(String id) {
        return this.inscriptionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Inscription with id {} not found", id);
                    return new NotFoundException("Inscription not found");
                });
    }

}
