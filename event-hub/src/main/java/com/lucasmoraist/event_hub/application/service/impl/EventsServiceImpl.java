package com.lucasmoraist.event_hub.application.service.impl;

import com.lucasmoraist.event_hub.application.events.EventsPersistence;
import com.lucasmoraist.event_hub.application.service.EventsService;
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

    private final EventsPersistence eventsPersistence;

    @Override
    public void createEvent(EventsRequest request) {
        this.eventsPersistence.createEvent(request);
    }

    @Override
    public EventsResponse findById(String id) {
        return this.eventsPersistence.findById(id);
    }

    @Override
    public void updateEvent(String id, EventsRequest request) {
        this.eventsPersistence.updateEvent(id, request);
    }

    @Override
    public void deleteEvent(String id) {
        this.eventsPersistence.deleteEvent(id);
    }

    @Override
    public List<EventsResponse> findAll() {
        return this.eventsPersistence.findAll();
    }

    @Override
    public List<EventsResponse> listUpComing() {
        return this.eventsPersistence.listUpComing();
    }

    @Override
    public List<EventsResponse> listAvailableEvents() {
        return this.eventsPersistence.listAvailableEvents();
    }

}
