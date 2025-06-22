package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.application.service.UserService;
import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.enums.StatusInscriptions;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.producer.InscriptionProducer;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscribeUseCaseTest {

    @Mock
    InscriptionsRepository inscriptionRepository;
    @Mock
    UserService userService;
    @Mock
    EventsService eventsService;
    @Mock
    InscriptionProducer producer;
    @InjectMocks
    SubscribeUseCase subscribeUseCase;

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
    @DisplayName("Should subscribe user to event and return InscriptionResponse")
    void case01() {
        Inscriptions inscriptions = new Inscriptions(userId, eventId);
        inscriptions.setId("1234567890");

        when(userService.getUserById(userId)).thenReturn(user);
        when(eventsService.getEventById(eventId)).thenReturn(event);
        when(inscriptionRepository.save(any(Inscriptions.class))).thenReturn(inscriptions);

        var response = subscribeUseCase.execute(userId, eventId);

        assertAll("Response",
                () -> assertEquals(user.name(), response.userName()),
                () -> assertEquals(user.email(), response.userEmail()),
                () -> assertEquals(event.title(), response.eventTitle()),
                () -> assertEquals(event.date(), response.eventDate()),
                () -> assertEquals(event.time(), response.eventTime()),
                () -> assertEquals(event.location(), response.eventLocation()),
                () -> assertEquals(StatusInscriptions.PENDING.name(), response.status())
        );
        assertAll("Verifier",
                () -> verify(inscriptionRepository, times(1)).save(any(Inscriptions.class)),
                () -> verify(userService, times(1)).getUserById(userId),
                () -> verify(eventsService, times(1)).getEventById(eventId),
                () -> verify(producer, times(1)).sendMessage(any())
        );
    }

}