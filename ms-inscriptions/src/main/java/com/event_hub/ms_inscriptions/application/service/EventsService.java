package com.event_hub.ms_inscriptions.application.service;

import com.event_hub.ms_inscriptions.domain.model.EventsData;

import java.util.List;

public interface EventsService {
    EventsData getEventById(String eventId);
    List<EventsData> getAllEvents();
}
