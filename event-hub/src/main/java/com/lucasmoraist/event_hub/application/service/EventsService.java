package com.lucasmoraist.event_hub.application.service;

import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;

import java.util.List;

public interface EventsService {
    void createEvent(EventsRequest request);
    EventsResponse findById(String id);
    void updateEvent(String id, EventsRequest request);
    void deleteEvent(String id);
    List<EventsResponse> findAll();
    List<EventsResponse> listUpComing();
    List<EventsResponse> listAvailableEvents();
}
