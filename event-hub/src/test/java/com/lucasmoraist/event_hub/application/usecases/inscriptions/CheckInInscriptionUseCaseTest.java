package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.domain.exception.StatusException;
import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckInInscriptionUseCaseTest {

    @Mock
    InscriptionsRepository inscriptionRepository;
    @Mock
    GetInscriptionById getInscriptionById;
    @InjectMocks
    CheckInInscriptionUseCase checkInInscriptionUseCase;

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
    @DisplayName("Should check in an inscription successfully")
    void case01() {
        Inscriptions inscriptions = new Inscriptions(userId, eventId);
        inscriptions.setStatus(StatusInscriptions.CONFIRMED);
        inscriptions.setId("123456");

        when(getInscriptionById.execute(inscriptions.getId())).thenReturn(inscriptions);

        checkInInscriptionUseCase.execute(inscriptions.getId());

        assertEquals(StatusInscriptions.CHECKED_IN, inscriptions.getStatus(), "Inscription status should be CHECKED_IN");
        assertAll("Verifier",
                () -> verify(inscriptionRepository, times(1)).save(inscriptions),
                () -> verify(getInscriptionById, times(1)).execute(inscriptions.getId())
        );
    }

    @Test
    @DisplayName("Should throw StatusException when inscription is not confirmed")
    void case02() {
        Inscriptions inscriptions = new Inscriptions(userId, eventId);
        inscriptions.setId("123456");

        when(getInscriptionById.execute(inscriptions.getId())).thenReturn(inscriptions);

        StatusException exception = assertThrows(StatusException.class, () -> checkInInscriptionUseCase.execute(inscriptions.getId()));

        assertEquals("Inscription is not confirmed", exception.getMessage(), "Exception message should match");
        assertAll("Verifier",
                () -> verify(inscriptionRepository, times(0)).save(inscriptions),
                () -> verify(getInscriptionById, times(1)).execute(inscriptions.getId())
        );

    }

}