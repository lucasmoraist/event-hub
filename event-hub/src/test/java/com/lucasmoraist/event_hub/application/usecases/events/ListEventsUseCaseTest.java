package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListEventsUseCaseTest {

    @Mock
    EventsRepository repository;
    @InjectMocks
    ListEventsUseCase listEventsUseCase;

    EventsRequest request;
    EventsResponse response;
    Events events;

    @BeforeEach
    void setUp() {
        request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .location("Convention Center, Cityville")
                .createdBy("admin")
                .build();
        events = new Events(request);
    }

    @Test
    @DisplayName("Should list all events")
    void case01() {
        response = new EventsResponse(events);

        when(repository.findAll()).thenReturn(List.of(events));

        var eventsList = listEventsUseCase.execute();

        assertAll(
                () -> assertNotNull(eventsList),
                () -> assertEquals(1, eventsList.size()),
                () -> assertEquals(response.title(), eventsList.get(0).title()),
                () -> assertEquals(response.description(), eventsList.get(0).description()),
                () -> assertEquals(response.date(), eventsList.get(0).date()),
                () -> assertEquals(response.time(), eventsList.get(0).time()),
                () -> assertEquals(response.location(), eventsList.get(0).location())
        );
    }

    @Test
    @DisplayName("Should return an empty list when no events are available")
    void case02() {
        when(repository.findAll()).thenReturn(List.of());

        var eventsList = listEventsUseCase.execute();

        assertAll(
                () -> assertNotNull(eventsList),
                () -> assertTrue(eventsList.isEmpty())
        );
    }

}