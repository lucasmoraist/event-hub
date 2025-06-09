package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.application.usecases.events.GetEventById;
import com.lucasmoraist.event_hub.application.usecases.users.GetUserById;
import com.lucasmoraist.event_hub.application.utils.InscriptionResponseUtils;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class FindInscriptionByUserIdUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final GetUserById getUserById;
    private final GetEventById getEventById;

    public List<InscriptionResponse> execute(String userId) {
        log.info("Finding inscriptions for user with id {}", userId);
        return this.inscriptionRepository.findByUserId(userId).stream()
                .map(i -> {
                    User user = this.getUserById.execute(i.getUserId());
                    Events event = this.getEventById.execute(i.getEventId());
                    return InscriptionResponseUtils.build(i, user, event);
                })
                .toList();
    }

}
