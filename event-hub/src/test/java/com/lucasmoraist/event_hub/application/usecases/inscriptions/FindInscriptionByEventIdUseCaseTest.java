package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.application.usecases.events.GetEventById;
import com.lucasmoraist.event_hub.application.usecases.users.GetUserById;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import com.lucasmoraist.event_hub.infra.repository.InscriptionsRepository;
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
class FindInscriptionByEventIdUseCaseTest {

    @Mock
    InscriptionsRepository inscriptionRepository;
    @Mock
    GetUserById getUserById;
    @Mock
    GetEventById getEventById;
    @InjectMocks
    FindInscriptionByEventIdUseCase findInscriptionByEventIdUseCase;

    String userId;
    String eventId;
    User user;
    Events event;

    @BeforeEach
    void setUp() {
        userId = "123456";
        eventId = "654321";

        EventsRequest eventsRequest = EventsRequest.builder()
                .title("Event Name")
                .description("Event Description")
                .date(LocalDate.of(2023, 10, 1))
                .time(LocalTime.of(10, 0))
                .location("Location")
                .capacity(100)
                .createdBy("Organizer Name")
                .build();
        UserRequest userRequest = UserRequest.builder()
                .name("John Doe")
                .email("john@doe.com")
                .password("password123")
                .build();

        event = new Events(eventsRequest);
        event.setId(eventId);
        user = new User(userRequest);
        user.setId(userId);
    }

    @Test
    @DisplayName("Find inscriptions by event ID")
    void case01() {
        Inscriptions inscription = new Inscriptions(userId, eventId);

        when(inscriptionRepository.findByEventId(eventId)).thenReturn(List.of(inscription));
        when(getUserById.execute(userId)).thenReturn(user);
        when(getEventById.execute(eventId)).thenReturn(event);

        List<InscriptionResponse> response = findInscriptionByEventIdUseCase.execute(eventId);

        assertAll("Response",
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size()),
                () -> assertEquals(user.getName(), response.get(0).userName()),
                () -> assertEquals(user.getEmail(), response.get(0).userEmail()),
                () -> assertEquals(event.getTitle(), response.get(0).eventTitle()),
                () -> assertEquals(event.getDate().toString(), response.get(0).eventDate()),
                () -> assertEquals(event.getTime().toString(), response.get(0).eventTime()),
                () -> assertEquals(event.getLocation(), response.get(0).eventLocation())
        );

        assertAll("Verifier",
                () -> verify(inscriptionRepository, times(1)).findByEventId(eventId),
                () -> verify(getUserById, times(1)).execute(userId),
                () -> verify(getEventById, times(1)).execute(eventId)
                );
    }

}