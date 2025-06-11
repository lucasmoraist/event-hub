package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.domain.entity.Events;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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