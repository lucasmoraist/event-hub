package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.domain.entity.Events;
import com.event_hub.ms_events.domain.exception.NotFoundException;
import com.event_hub.ms_events.infra.controller.request.EventsRequest;
import com.event_hub.ms_events.infra.controller.response.EventsResponse;
import com.event_hub.ms_events.infra.repository.EventsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindEventByIdTest {

    @Mock
    EventsRepository repository;
    @InjectMocks
    FindEventById findEventById;

    String eventId;
    EventsRequest request;
    Events events;
    EventsResponse response;

    @BeforeEach
    void setUp() {
        eventId = "12345";
        request = EventsRequest.builder()
                .title("Event Name")
                .description("Event Description")
                .date(LocalDate.of(2023, 10, 1))
                .time(LocalTime.of(10, 0))
                .location("Location")
                .capacity(100)
                .createdBy("Organizer Name")
                .build();
        events = new Events(request);
        response = new EventsResponse(events);
    }

    @Test
    @DisplayName("Should return event by id")
    void case01() {
        when(repository.findById(eventId)).thenReturn(Optional.of(events));

        var find = findEventById.execute(eventId);

        assertAll(
                () -> assertNotNull(eventId),
                () -> assertNotNull(find),
                () -> assertEquals(response.id(), find.id()),
                () -> assertEquals(response.title(), find.title()),
                () -> assertEquals(response.description(), find.description()),
                () -> assertEquals(response.date(), find.date()),
                () -> assertEquals(response.time(), find.time()),
                () -> assertEquals(response.location(), find.location()),
                () -> assertEquals(response.capacity(), find.capacity()),
                () -> assertEquals(response.createdBy(), find.createdBy()),
                () -> verify(repository, times(1))
                        .findById(eventId)
        );
    }

    @Test
    @DisplayName("Should throw NotFoundException when event not found")
    void case02() {
        when(repository.findById(eventId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> findEventById.execute(eventId));

        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Event not found", exception.getMessage()),
                () -> verify(repository, times(1))
                        .findById(eventId)
        );
    }

}