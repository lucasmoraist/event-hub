package com.event_hub.ms_events.application.service;

import com.event_hub.ms_events.infra.controller.request.EventsRequest;
import com.event_hub.ms_events.infra.controller.response.EventsResponse;

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
