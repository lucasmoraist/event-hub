package com.event_hub.ms_inscriptions.application.service.impl;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.client.EventsClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final EventsClient eventsClient;
    private final ObjectMapper objectMapper;

    @Override
    public EventsData getEventById(String eventId) {
        log.debug("Fetching event data for eventId: {}", eventId);

        try {
            Response response = this.eventsClient.getEventById(eventId);
            return parseEventData(response);
        } catch (Exception e) {
            log.error("Error fetching event data for eventId: {}", eventId, e);
            throw new RuntimeException("Failed to fetch event data", e);
        }
    }

    @Override
    public List<EventsData> getAllEvents() {
        log.debug("Fetching all events data");

        try {
            Response response = this.eventsClient.getAllEvents();
            return parseEventsData(response);
        } catch (Exception e) {
            log.error("Error parsing all events data from response", e);
            throw new RuntimeException(e);
        }
    }

    private EventsData parseEventData(Response response) {
        if (response == null || response.body() == null) {
            log.warn("Received null response or body for user data");
            return null;
        }

        try (InputStream in = response.body().asInputStream()) {
            EventsData eventsData = objectMapper.readValue(in, EventsData.class);
            log.info("Successfully parsed event data: {}", eventsData);
            return eventsData;
        } catch (IOException e) {
            log.error("Error parsing user data from response", e);
            throw new RuntimeException(e);
        }
    }

    private List<EventsData> parseEventsData(Response response) {
        if (response == null || response.body() == null) {
            log.warn("Received null response or body for user data");
            return null;
        }

        try (InputStream in = response.body().asInputStream()) {
            List<EventsData> eventsDataList = objectMapper.readValue(in, objectMapper.getTypeFactory().constructCollectionType(List.class, EventsData.class));
            log.info("Successfully parsed events data: {}", eventsDataList);
            return eventsDataList;
        } catch (IOException e) {
            log.error("Error parsing user data from response", e);
            throw new RuntimeException(e);
        }
    }

}
