package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.domain.entity.Events;
import com.event_hub.ms_events.domain.exception.InvalidFieldException;
import com.event_hub.ms_events.domain.exception.NotFoundException;
import com.event_hub.ms_events.infra.controller.request.EventsRequest;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEventUseCaseTest {

    @Mock
    EventsRepository repository;
    @Mock
    GetEventById getEventById;
    @InjectMocks
    UpdateEventUseCase updateEventUseCase;

    String eventId;

    @BeforeEach
    void setUp() {
        eventId = "12345";
    }

    @Test
    @DisplayName("Should update an event")
    void case01() {
        EventsRequest request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .capacity(500)
                .build();
        Events events = new Events(request);

        when(getEventById.execute(eventId)).thenReturn(events);

        updateEventUseCase.execute(eventId, request);

        assertAll(
                () -> assertEquals(request.title(), events.getTitle()),
                () -> assertEquals(request.description(), events.getDescription()),
                () -> assertEquals(request.date(), events.getDate()),
                () -> assertEquals(request.time(), events.getTime()),
                () -> assertEquals(request.capacity(), events.getCapacity()),
                () -> verify(repository, times(1))
                        .save(events)
        );
    }

    @Test
    @DisplayName("Should throw NotFoundException when event not found")
    void case02() {
        EventsRequest request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .capacity(500)
                .build();

        when(getEventById.execute(eventId)).thenThrow(new NotFoundException("Event not found"));

        Exception exception = assertThrows(NotFoundException.class, () -> updateEventUseCase.execute(eventId, request));

        assertEquals("Event not found", exception.getMessage());
        verify(getEventById, times(1))
                .execute(eventId);
        verify(repository, times(0))
                .delete(any(Events.class));

    }

    @Test
    @DisplayName("Should throw InvalidFieldException when trying to update location")
    void case03() {
        EventsRequest request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .location("New Location")
                .capacity(500)
                .build();
        Events events = new Events(request);

        when(getEventById.execute(eventId)).thenReturn(events);

        Exception exception = assertThrows(InvalidFieldException.class, () -> updateEventUseCase.execute(eventId, request));

        assertEquals("Location cannot be updated", exception.getMessage());
        verify(repository, times(0))
                .save(any(Events.class));
    }

    @Test
    @DisplayName("Should throw InvalidFieldException when trying to update createdBy")
    void case04() {
        EventsRequest request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .createdBy("newUser")
                .capacity(500)
                .build();
        Events events = new Events(request);

        when(getEventById.execute(eventId)).thenReturn(events);

        Exception exception = assertThrows(InvalidFieldException.class, () -> updateEventUseCase.execute(eventId, request));

        assertEquals("Created by cannot be updated", exception.getMessage());
        verify(repository, times(0))
                .save(any(Events.class));
    }

    @Test
    @DisplayName("Should throw InvalidFieldException when capacity is negative")
    void case05() {
        EventsRequest request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .capacity(-100)
                .build();
        Events events = new Events(request);

        when(getEventById.execute(eventId)).thenReturn(events);

        Exception exception = assertThrows(InvalidFieldException.class, () -> updateEventUseCase.execute(eventId, request));

        assertEquals("Capacity cannot be negative", exception.getMessage());
        verify(repository, times(0))
                .save(any(Events.class));
    }

}