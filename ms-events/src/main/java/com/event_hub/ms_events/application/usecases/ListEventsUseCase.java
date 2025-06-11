package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.infra.controller.response.EventsResponse;
import com.event_hub.ms_events.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListEventsUseCase {

    private final EventsRepository repository;

    public List<EventsResponse> execute() {
        log.info("Fetching all events");
        return this.repository.findAll()
                .stream()
                .map(EventsResponse::new)
                .toList();
    }

}
