package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.application.service.UserService;
import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.controller.response.InscriptionResponse;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindInscriptionByUserIdUseCaseTest {

    @Mock
    InscriptionsRepository inscriptionRepository;
    @Mock
    UserService userService;
    @Mock
    EventsService eventsService;
    @InjectMocks
    FindInscriptionByUserIdUseCase findInscriptionByUserIdUseCase;

    String userId;
    String eventId;
    UserData user;
    EventsData event;

    @BeforeEach
    void setUp() {
        userId = "123456";
        eventId = "654321";

        event = EventsData.builder()
                .id(eventId)
                .title("Event Name")
                .description("Event Description")
                .date("2023-10-01")
                .time("10:00")
                .location("Location")
                .capacity(100)
                .createdBy("Organizer Name")
                .build();
        user = UserData.builder()
                .id(userId)
                .name("John Doe")
                .email("john@doe.com")
                .roles("USER")
                .build();
    }

    @Test
    @DisplayName("Find inscriptions by user ID")
    void case01() {
        Inscriptions inscription = new Inscriptions(userId, eventId);

        when(inscriptionRepository.findByUserId(eventId)).thenReturn(List.of(inscription));
        when(userService.getUserById(userId)).thenReturn(user);
        when(eventsService.getEventById(eventId)).thenReturn(event);

        List<InscriptionResponse> response = findInscriptionByUserIdUseCase.execute(eventId);

        assertAll("Response",
                () -> assertNotNull(response),
                () -> assertEquals(1, response.size()),
                () -> assertEquals(user.name(), response.get(0).userName()),
                () -> assertEquals(user.email(), response.get(0).userEmail()),
                () -> assertEquals(event.title(), response.get(0).eventTitle()),
                () -> assertEquals(event.date(), response.get(0).eventDate()),
                () -> assertEquals(event.time(), response.get(0).eventTime()),
                () -> assertEquals(event.location(), response.get(0).eventLocation())
        );

        assertAll("Verifier",
                () -> verify(inscriptionRepository, times(1)).findByUserId(eventId),
                () -> verify(userService, times(1)).getUserById(userId),
                () -> verify(eventsService, times(1)).getEventById(eventId)
        );
    }

}