package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.exception.NotFoundException;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEventByIdTest {

    @Mock
    EventsRepository repository;
    @InjectMocks
    GetEventById getEventById;

    String eventId;
    EventsRequest request;
    Events events;

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
    }

    @Test
    @DisplayName("Should return event by id")
    void case01() {
        when(repository.findById(eventId)).thenReturn(Optional.of(events));

        var find = getEventById.execute(eventId);

        assertAll(
                () -> assertNotNull(eventId),
                () -> assertNotNull(find),
                () -> assertEquals(events.getId(), find.getId()),
                () -> assertEquals(events.getTitle(), find.getTitle()),
                () -> assertEquals(events.getDescription(), find.getDescription()),
                () -> assertEquals(events.getDate(), find.getDate()),
                () -> assertEquals(events.getTime(), find.getTime()),
                () -> assertEquals(events.getLocation(), find.getLocation()),
                () -> assertEquals(events.getCapacity(), find.getCapacity()),
                () -> assertEquals(events.getCreatedBy(), find.getCreatedBy()),
                () -> verify(repository, times(1))
                        .findById(eventId)
        );
    }

    @Test
    @DisplayName("Should throw NotFoundException when event not found")
    void case02() {
        when(repository.findById(eventId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> getEventById.execute(eventId));

        assertAll(
                () -> assertNotNull(exception),
                () -> assertEquals("Event not found", exception.getMessage()),
                () -> verify(repository, times(1))
                        .findById(eventId)
        );
    }

}