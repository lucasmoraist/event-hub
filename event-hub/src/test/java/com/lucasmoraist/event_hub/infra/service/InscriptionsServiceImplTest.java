package com.lucasmoraist.event_hub.infra.service;

import com.lucasmoraist.event_hub.infra.service.impl.InscriptionsServiceImpl;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.CancelInscriptionUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.CheckInInscriptionUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.ConfirmInscriptionUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.FindInscriptionByEventIdUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.FindInscriptionByUserIdUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.ListWaitingListUseCase;
import com.lucasmoraist.event_hub.application.usecases.inscriptions.SubscribeUseCase;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscriptionsServiceImplTest {

    @Mock
    CancelInscriptionUseCase cancelInscriptionUseCase;
    @Mock
    CheckInInscriptionUseCase checkInInscriptionUseCase;
    @Mock
    ConfirmInscriptionUseCase confirmInscriptionUseCase;
    @Mock
    FindInscriptionByEventIdUseCase findInscriptionByEventIdUseCase;
    @Mock
    FindInscriptionByUserIdUseCase findInscriptionByUserIdUseCase;
    @Mock
    ListWaitingListUseCase listWaitingListUseCase;
    @Mock
    SubscribeUseCase subscribeUseCase;
    @InjectMocks
    InscriptionsServiceImpl inscriptionsService;

    String userId;
    String inscriptionsId;
    String eventId;
    InscriptionResponse inscriptionResponse;

    @BeforeEach
    void setUp() {
        userId = "user123";
        inscriptionsId = "inscription123";
        eventId = "event123";
        inscriptionResponse = InscriptionResponse.builder()
                .id(inscriptionsId)
                .userName("John Doe")
                .userEmail("john@doe.com")
                .eventTitle("Sample Event")
                .eventDate("2023-10-01")
                .eventTime("10:00 AM")
                .eventLocation("123 Event St")
                .status("PENDING")
                .build();
    }

    @Test
    @DisplayName("Test subscribe method")
    void case01() {
        when(subscribeUseCase.execute(userId, eventId)).thenReturn(inscriptionResponse);

        var response = inscriptionsService.subscribe(userId, eventId);

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(inscriptionResponse.id(), response.id(), "Inscription ID should match"),
                () -> assertEquals(inscriptionResponse.userName(), response.userName(), "User name should match"),
                () -> assertEquals(inscriptionResponse.userEmail(), response.userEmail(), "User email should match"),
                () -> assertEquals(inscriptionResponse.eventTitle(), response.eventTitle(), "Event title should match"),
                () -> assertEquals(inscriptionResponse.eventDate(), response.eventDate(), "Event date should match"),
                () -> assertEquals(inscriptionResponse.eventTime(), response.eventTime(), "Event time should match"),
                () -> assertEquals(inscriptionResponse.eventLocation(), response.eventLocation(), "Event location should match"),
                () -> assertEquals(inscriptionResponse.status(), response.status(), "Status should match"),
                () -> verify(subscribeUseCase, times(1))
                        .execute(userId, eventId)
        );
    }

    @Test
    @DisplayName("Test confirm method")
    void case02() {
        when(confirmInscriptionUseCase.execute(inscriptionsId)).thenReturn(inscriptionResponse);

        var response = inscriptionsService.confirm(inscriptionsId);

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(inscriptionResponse.id(), response.id(), "Inscription ID should match"),
                () -> assertEquals(inscriptionResponse.userName(), response.userName(), "User name should match"),
                () -> assertEquals(inscriptionResponse.userEmail(), response.userEmail(), "User email should match"),
                () -> assertEquals(inscriptionResponse.eventTitle(), response.eventTitle(), "Event title should match"),
                () -> assertEquals(inscriptionResponse.eventDate(), response.eventDate(), "Event date should match"),
                () -> assertEquals(inscriptionResponse.eventTime(), response.eventTime(), "Event time should match"),
                () -> assertEquals(inscriptionResponse.eventLocation(), response.eventLocation(), "Event location should match"),
                () -> assertEquals(inscriptionResponse.status(), response.status(), "Status should match"),
                () -> verify(confirmInscriptionUseCase, times(1))
                        .execute(inscriptionsId)
        );
    }

    @Test
    @DisplayName("Test cancel method")
    void case03() {
        when(cancelInscriptionUseCase.execute(inscriptionsId)).thenReturn(inscriptionResponse);

        var response = inscriptionsService.cancel(inscriptionsId);

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(inscriptionResponse.id(), response.id(), "Inscription ID should match"),
                () -> assertEquals(inscriptionResponse.userName(), response.userName(), "User name should match"),
                () -> assertEquals(inscriptionResponse.userEmail(), response.userEmail(), "User email should match"),
                () -> assertEquals(inscriptionResponse.eventTitle(), response.eventTitle(), "Event title should match"),
                () -> assertEquals(inscriptionResponse.eventDate(), response.eventDate(), "Event date should match"),
                () -> assertEquals(inscriptionResponse.eventTime(), response.eventTime(), "Event time should match"),
                () -> assertEquals(inscriptionResponse.eventLocation(), response.eventLocation(), "Event location should match"),
                () -> assertEquals(inscriptionResponse.status(), response.status(), "Status should match"),
                () -> verify(cancelInscriptionUseCase, times(1))
                        .execute(inscriptionsId)
        );
    }

    @Test
    @DisplayName("Test findByUserId method")
    void case04() {
        when(findInscriptionByUserIdUseCase.execute(userId)).thenReturn(List.of(inscriptionResponse));

        var response = inscriptionsService.findByUserId(userId);

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertFalse(response.isEmpty(), "Response list should not be empty"),
                () -> assertEquals(1, response.size(), "Response list size should match"),
                () -> assertEquals(inscriptionResponse.id(), response.get(0).id(), "Inscription ID should match"),
                () -> verify(findInscriptionByUserIdUseCase, times(1))
                        .execute(userId)
        );
    }

    @Test
    @DisplayName("Test findByEventId method")
    void case05() {
        when(findInscriptionByEventIdUseCase.execute(eventId)).thenReturn(List.of(inscriptionResponse));

        var response = inscriptionsService.findByEventId(eventId);

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertFalse(response.isEmpty(), "Response list should not be empty"),
                () -> assertEquals(1, response.size(), "Response list size should match"),
                () -> assertEquals(inscriptionResponse.id(), response.get(0).id(), "Inscription ID should match"),
                () -> verify(findInscriptionByEventIdUseCase, times(1))
                        .execute(eventId)
        );
    }

    @Test
    @DisplayName("Test listWaitingList method")
    void case06() {
        when(listWaitingListUseCase.execute(eventId)).thenReturn(List.of(inscriptionResponse));

        var response = inscriptionsService.listWaitingList(eventId);

        assertAll(
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertFalse(response.isEmpty(), "Response list should not be empty"),
                () -> assertEquals(1, response.size(), "Response list size should match"),
                () -> assertEquals(inscriptionResponse.id(), response.get(0).id(), "Inscription ID should match"),
                () -> verify(listWaitingListUseCase, times(1))
                        .execute(eventId)
        );
    }

    @Test
    @DisplayName("Test checkIn method")
    void case07() {
        inscriptionsService.checkIn(inscriptionsId);

        verify(checkInInscriptionUseCase, times(1))
                .execute(inscriptionsId);
    }

}