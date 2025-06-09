package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.application.usecases.events.GetEventById;
import com.lucasmoraist.event_hub.application.usecases.users.GetUserById;
import com.lucasmoraist.event_hub.application.usecases.utils.InscriptionResponseUtils;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListWaitingListUseCase {

    private final InscriptionsRepository inscriptionRepository;
    private final GetUserById getUserById;
    private final GetEventById getEventById;

    public List<InscriptionResponse> execute(String eventId) {
        log.info("Listing waiting list for event with id {}", eventId);
        return this.inscriptionRepository.findByEventId(eventId).stream()
                .filter(i -> i.getStatus() == StatusInscriptions.PENDING)
                .map(i -> {
                    User user = this.getUserById.execute(i.getUserId());
                    Events event = this.getEventById.execute(eventId);
                    return InscriptionResponseUtils.build(i, user, event);
                })
                .toList();
    }

}
