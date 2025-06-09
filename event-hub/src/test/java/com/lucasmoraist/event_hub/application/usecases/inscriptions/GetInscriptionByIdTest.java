package com.lucasmoraist.event_hub.application.usecases.inscriptions;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.exception.NotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetInscriptionByIdTest {

    @Mock
    InscriptionsRepository inscriptionRepository;
    @InjectMocks
    GetInscriptionById getInscriptionById;

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
    @DisplayName("Should return inscription by id")
    void shouldReturnInscriptionById() {
        Inscriptions inscription = new Inscriptions(userId, eventId);
        inscription.setId("123456789");

        when(inscriptionRepository.findById(inscription.getId())).thenReturn(Optional.of(inscription));

        Inscriptions response = getInscriptionById.execute(inscription.getId());

        assertAll("Response",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(inscription.getId(), response.getId(), "Inscription ID should match"),
                () -> assertEquals(userId, response.getUserId(), "User ID should match"),
                () -> assertEquals(eventId, response.getEventId(), "Event ID should match")
        );
        verify(inscriptionRepository, times(1)).findById(inscription.getId());
    }

    @Test
    @DisplayName("Should throw NotFoundException when inscription not found")
    void shouldThrowNotFoundExceptionWhenInscriptionNotFound() {
        String inscriptionId = "123456789";

        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> getInscriptionById.execute(inscriptionId));

        assertEquals("Inscription not found", exception.getMessage(), "Exception message should match");
    }

}