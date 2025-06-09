package com.lucasmoraist.event_hub.application.usecases.events;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.exception.NotFoundException;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteEventUseCaseTest {

    @Mock
    EventsRepository repository;
    @Mock
    GetEventById getEventById;
    @InjectMocks
    DeleteEventUseCase deleteEventUseCase;

    @Test
    @DisplayName("Should delete an event successfully")
    void case01() {
        String id = "12345";
        Events events = new Events();
        events.setId(id);

        when(getEventById.execute(id)).thenReturn(events);

        deleteEventUseCase.execute(id);

        assertNotNull(id);
        verify(getEventById, times(1))
                .execute(id);
        verify(repository, times(1))
                .delete(events);
    }

    @Test
    @DisplayName("Should throw an exception when event does not exist")
    void case02() {
        String id = "12345";

        when(getEventById.execute(id)).thenThrow(new NotFoundException("Event not found"));

        Exception exception = assertThrows(NotFoundException.class, () -> deleteEventUseCase.execute(id));

        assertEquals("Event not found", exception.getMessage());
        verify(getEventById, times(1))
                .execute(id);
        verify(repository, times(0))
                .delete(any(Events.class));
    }

}