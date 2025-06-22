package com.event_hub.ms_inscriptions.application.usecases;

import com.event_hub.ms_inscriptions.application.service.EventsService;
import com.event_hub.ms_inscriptions.domain.entity.Inscriptions;
import com.event_hub.ms_inscriptions.domain.enums.StatusInscriptions;
import com.event_hub.ms_inscriptions.domain.model.EventsData;
import com.event_hub.ms_inscriptions.infra.repository.InscriptionsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpireAllForEventUseCaseTest {

    @Mock
    InscriptionsRepository inscriptionRepository;
    @Mock
    EventsService eventsService;
    @InjectMocks
    ExpireAllForEventUseCase expireAllForEventUseCase;

    @Test
    @DisplayName("Should expire all pending inscriptions for an event")
    void case01() {
        String eventId = "event123";
        EventsData expiredEvent = EventsData.builder()
                .id(eventId)
                .date(LocalDate.now().minusDays(1).toString())
                .time(LocalTime.now().toString())
                .build();

        Inscriptions pendingInscription = new Inscriptions("user123", eventId);
        pendingInscription.setStatus(StatusInscriptions.PENDING);

        Inscriptions confirmedInscription = new Inscriptions("user456", eventId);
        confirmedInscription.setStatus(StatusInscriptions.CONFIRMED);

        List<EventsData> events = List.of(expiredEvent);
        List<Inscriptions> inscriptions = List.of(pendingInscription, confirmedInscription);

        when(eventsService.getAllEvents()).thenReturn(events);
        when(inscriptionRepository.findByEventId(eventId)).thenReturn(inscriptions);

        expireAllForEventUseCase.execute();

        verify(inscriptionRepository, times(1)).findByEventId(eventId);
        verify(inscriptionRepository, times(1)).save(pendingInscription);
        verify(inscriptionRepository, times(1)).save(confirmedInscription);

        assert pendingInscription.getStatus() == StatusInscriptions.EXPIRED;
        assert confirmedInscription.getStatus() == StatusInscriptions.EXPIRED;
    }

}