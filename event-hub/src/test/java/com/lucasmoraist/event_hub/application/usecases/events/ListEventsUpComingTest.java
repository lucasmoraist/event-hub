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
class ListEventsUpComingTest {

    @Mock
    EventsRepository repository;
    @InjectMocks
    ListEventsUpComing listEventsUpComing;

    EventsRequest request;
    EventsResponse response;
    Events events;

    @BeforeEach
    void setUp() {
        request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .time(LocalTime.of(9, 0))
                .location("Convention Center, Cityville")
                .capacity(500)
                .createdBy("admin")
                .build();
        events = new Events(request);
    }

    @Test
    @DisplayName("Should list all upcoming events")
    void case01() {
        events.setDate(LocalDate.now().plusDays(30));
        response = new EventsResponse(events);

        when(repository.findAll()).thenReturn(List.of(events));

        var events = listEventsUpComing.execute();

        assertAll(
                () -> assertNotNull(events),
                () -> assertEquals(1, events.size()),
                () -> assertEquals(response.title(), events.get(0).title()),
                () -> assertEquals(response.description(), events.get(0).description()),
                () -> assertEquals(response.date(), events.get(0).date()),
                () -> assertEquals(response.time(), events.get(0).time()),
                () -> assertEquals(response.location(), events.get(0).location()),
                () -> assertEquals(response.capacity(), events.get(0).capacity())
        );
    }

    @Test
    @DisplayName("Should return an empty list when there are no upcoming events")
    void case02() {
        when(repository.findAll()).thenReturn(List.of());

        var events = listEventsUpComing.execute();

        assertAll(
                () -> assertNotNull(events),
                () -> assertTrue(events.isEmpty())
        );
    }

}