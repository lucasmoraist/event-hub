package com.event_hub.ms_inscriptions.application.service;

import com.event_hub.ms_inscriptions.application.service.impl.EventsServiceImpl;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.infra.client.EventsClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventsServiceImplTest {

    @Mock
    EventsClient client;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    EventsServiceImpl service;

    @Test
    @DisplayName("Should return EventsData when getEventById is called")
    void case01() throws Exception {
        EventsData eventsData = new EventsData(
                "123",
                "Event Name",
                "Event Description",
                "2023-10-01T10:00:00Z",
                "2023-10-01T12:00:00Z",
                "Location",
                100,
                "Organizer"
        );

        when(client.getEventById(anyString())).thenReturn(mockResponse());
        when(objectMapper.readValue(any(InputStream.class), eq(EventsData.class))).thenReturn(eventsData);

        EventsData result = service.getEventById("123");

        assertAll("Result",
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertEquals(eventsData.id(), result.id(), "Event ID should match"),
                () -> assertEquals(eventsData.title(), result.title(), "Event title should match"),
                () -> assertEquals(eventsData.description(), result.description(), "Event description should match"),
                () -> assertEquals(eventsData.date(), result.date(), "Event date should match"),
                () -> assertEquals(eventsData.time(), result.time(), "Event time should match"),
                () -> assertEquals(eventsData.location(), result.location(), "Event location should match"),
                () -> assertEquals(eventsData.capacity(), result.capacity(), "Event capacity should match"),
                () -> assertEquals(eventsData.createdBy(), result.createdBy(), "Event createdBy should match")
        );

        assertAll("Verify",
                () -> verify(client, times(1))
                        .getEventById(anyString()),
                () -> verify(objectMapper, times(1))
                        .readValue(any(InputStream.class), eq(EventsData.class))
        );
    }

    private Response mockResponse() {
        Request mockRequest = Request.create(
                Request.HttpMethod.GET,
                "/events/123",
                Collections.emptyMap(),
                null,
                null,
                null
        );

        return Response.builder()
                .status(200)
                .request(mockRequest)
                .body(new ByteArrayInputStream("{\"id\":\"123\",\"title\":\"Event Name\",\"description\":\"Event Description\",\"date\":\"2023-10-01T10:00:00Z\",\"time\":\"2023-10-01T12:00:00Z\",\"location\":\"Location\",\"capacity\":100,\"createdBy\":\"Organizer\"}".getBytes()), null)
                .build();
    }

}