package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
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
                    return new RuntimeException("Inscription not found");
                });
    }

}
