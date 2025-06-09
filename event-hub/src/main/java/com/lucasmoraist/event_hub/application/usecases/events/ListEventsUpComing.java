package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListEventsUpComing {

    private final EventsRepository repository;

    public List<EventsResponse> execute() {
        log.info("Fetching all upcoming events");
        return this.repository.findAll()
                .stream()
                .filter(e -> e.getDate().isAfter(LocalDate.now()))
                .map(EventsResponse::new)
                .toList();
    }


}
