package com.lucasmoraist.event_hub.application.events;

import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;

import java.util.List;
import java.util.UUID;

public interface EventsPersistence {
    void createEvent(EventsRequest request);
    EventsResponse findById(UUID id);
    void updateEvent(UUID id, EventsRequest request);
    void deleteEvent(UUID id);
    List<EventsResponse> findAll();
    List<EventsResponse> listUpComing();
    List<EventsResponse> listAvailableEvents();
}
