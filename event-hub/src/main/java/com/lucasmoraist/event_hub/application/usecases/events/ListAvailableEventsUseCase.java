package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListAvailableEventsUseCase {

    private final EventsRepository repository;

    public List<EventsResponse> execute() {
        log.info("Fetching all available events");
        return this.repository.findAll()
                .stream()
                .filter(e -> e.getCapacity() > 0)
                .map(EventsResponse::new)
                .toList();
    }

}
