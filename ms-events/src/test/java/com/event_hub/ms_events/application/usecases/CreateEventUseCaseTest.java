package com.event_hub.ms_events.application.usecases;

import com.event_hub.ms_events.domain.entity.Events;
import com.event_hub.ms_events.domain.exception.SameEventException;
import com.event_hub.ms_events.infra.controller.request.EventsRequest;
import com.event_hub.ms_events.infra.repository.EventsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEventUseCaseTest {

    @Mock
    EventsRepository repository;
    @Mock
    MongoTemplate mongoTemplate;
    @InjectMocks
    CreateEventUseCase createEventUseCase;

    EventsRequest request;

    @BeforeEach
    void setUp() {
        request = EventsRequest.builder()
                .title("Tech Conference 2023")
                .description("A conference about the latest in technology.")
                .date(LocalDate.of(2023, 10, 15))
                .time(LocalTime.of(9, 0))
                .location("Convention Center, Cityville")
                .capacity(500)
                .createdBy("admin")
                .build();
    }

    @Test
    @DisplayName("Should create an event successfully")
    void case01() {
        Events events = new Events(request);
        when(repository.save(any(Events.class))).thenReturn(events);

        createEventUseCase.execute(request);

        assertNotNull(request);
        assertNotNull(events);
        verify(repository, times(1))
                .save(any(Events.class));
    }

    @Test
    @DisplayName("Should throw SameEventException when event with same name, date, time and location exists")
    void case02() {
        when(mongoTemplate.count(any(), any(Class.class))).thenReturn(1L);

        assertThrows(SameEventException.class, () -> createEventUseCase.execute(request));

        verify(repository, times(0))
                .save(any(Events.class));
        verify(mongoTemplate, times(1))
                .count(any(), any(Class.class));
    }

}