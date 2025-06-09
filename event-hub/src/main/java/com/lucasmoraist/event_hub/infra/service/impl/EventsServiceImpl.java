package com.lucasmoraist.event_hub.infra.service.impl;

import com.lucasmoraist.event_hub.infra.service.EventsService;
import com.lucasmoraist.event_hub.application.usecases.events.CreateEventUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.DeleteEventUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.FindEventById;
import com.lucasmoraist.event_hub.application.usecases.events.ListAvailableEventsUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.ListEventsUpComing;
import com.lucasmoraist.event_hub.application.usecases.events.ListEventsUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.UpdateEventUseCase;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final CreateEventUseCase createEventUseCase;
    private final DeleteEventUseCase deleteEventUseCase;
    private final FindEventById findEventById;
    private final ListAvailableEventsUseCase listAvailableEventsUseCase;
    private final ListEventsUpComing listEventsUpComing;
    private final ListEventsUseCase listEventsUseCase;
    private final UpdateEventUseCase updateEventUseCase;

    @Override
    public void createEvent(EventsRequest request) {
        this.createEventUseCase.execute(request);
    }

    @Override
    public EventsResponse findById(String id) {
        return this.findEventById.execute(id);
    }

    @Override
    public void updateEvent(String id, EventsRequest request) {
        this.updateEventUseCase.execute(id, request);
    }

    @Override
    public void deleteEvent(String id) {
        this.deleteEventUseCase.execute(id);
    }

    @Override
    public List<EventsResponse> findAll() {
        return this.listEventsUseCase.execute();
    }

    @Override
    public List<EventsResponse> listUpComing() {
        return this.listEventsUpComing.execute();
    }

    @Override
    public List<EventsResponse> listAvailableEvents() {
        return this.listAvailableEventsUseCase.execute();
    }

}
