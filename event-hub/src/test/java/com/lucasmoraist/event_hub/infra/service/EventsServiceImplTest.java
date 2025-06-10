package com.lucasmoraist.event_hub.infra.service;

import com.lucasmoraist.event_hub.infra.service.impl.EventsServiceImpl;
import com.lucasmoraist.event_hub.application.usecases.events.CreateEventUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.DeleteEventUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.FindEventById;
import com.lucasmoraist.event_hub.application.usecases.events.ListAvailableEventsUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.ListEventsUpComing;
import com.lucasmoraist.event_hub.application.usecases.events.ListEventsUseCase;
import com.lucasmoraist.event_hub.application.usecases.events.UpdateEventUseCase;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventsServiceImplTest {

    @Mock
    CreateEventUseCase createEventUseCase;
    @Mock
    DeleteEventUseCase deleteEventUseCase;
    @Mock
    FindEventById findEventById;
    @Mock
    ListAvailableEventsUseCase listAvailableEventsUseCase;
    @Mock
    ListEventsUpComing listEventsUpComing;
    @Mock
    ListEventsUseCase listEventsUseCase;
    @Mock
    UpdateEventUseCase updateEventUseCase;
    @InjectMocks
    EventsServiceImpl eventsService;

    String eventId;
    EventsRequest request;
    EventsResponse response;

    @BeforeEach
    void setUp() {
        eventId = "12345";
        request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .location("Convention Center, Cityville")
                .capacity(500)
                .createdBy("admin")
                .build();
        Events events = new Events(request);
        response = new EventsResponse(events);
    }

    @Test
    @DisplayName("Test createEvent method calls CreateEventUseCase")
    void case01() {
        eventsService.createEvent(request);

        assertNotNull(request);
        verify(createEventUseCase, times(1))
                .execute(request);
    }

    @Test
    @DisplayName("Test findById method calls FindEventById")
    void case02() {
        when(findEventById.execute(eventId))
                .thenReturn(response);

        var event = eventsService.findById(eventId);

        assertAll(
                () -> assertNotNull(eventId),
                () -> assertNotNull(response),
                () -> assertEquals(response.title(), event.title()),
                () -> assertEquals(response.description(), event.description()),
                () -> assertEquals(response.date(), event.date()),
                () -> assertEquals(response.time(), event.time()),
                () -> assertEquals(response.location(), event.location()),
                () -> assertEquals(response.capacity(), event.capacity()),
                () -> verify(findEventById, times(1))
                        .execute(eventId)
        );
    }

    @Test
    @DisplayName("Test updateEvent method calls UpdateEventUseCase")
    void case03() {
        eventsService.updateEvent(eventId, request);

        assertNotNull(eventId);
        assertNotNull(request);
        verify(updateEventUseCase, times(1))
                .execute(eventId, request);
    }

    @Test
    @DisplayName("Test deleteEvent method calls DeleteEventUseCase")
    void case04() {
        eventsService.deleteEvent(eventId);

        assertNotNull(eventId);
        verify(deleteEventUseCase, times(1))
                .execute(eventId);
    }

    @Test
    @DisplayName("Test findAll method calls ListEventsUseCase")
    void case05() {
        when(listEventsUseCase.execute())
                .thenReturn(List.of(response));

        var events = eventsService.findAll();

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(events.isEmpty()),
                () -> assertEquals(1, events.size()),
                () -> assertEquals(events.get(0).title(), "Tech Conference 2023"),
                () -> verify(listEventsUseCase, times(1))
                        .execute()
        );
    }

    @Test
    @DisplayName("Test listUpComing method calls ListEventsUpComing")
    void case06() {
        when(listEventsUpComing.execute())
                .thenReturn(List.of(response));

        var events = eventsService.listUpComing();

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(events.isEmpty()),
                () -> assertEquals(1, events.size()),
                () -> assertEquals(events.get(0).title(), "Tech Conference 2023"),
                () -> verify(listEventsUpComing, times(1))
                        .execute()
        );
    }

    @Test
    @DisplayName("Test listAvailableEvents method calls ListAvailableEventsUseCase")
    void case07() {
        when(listAvailableEventsUseCase.execute())
                .thenReturn(List.of(response));

        var events = eventsService.listAvailableEvents();

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(events.isEmpty()),
                () -> assertEquals(1, events.size()),
                () -> assertEquals(events.get(0).title(), "Tech Conference 2023"),
                () -> verify(listAvailableEventsUseCase, times(1))
                        .execute()
        );
    }

}