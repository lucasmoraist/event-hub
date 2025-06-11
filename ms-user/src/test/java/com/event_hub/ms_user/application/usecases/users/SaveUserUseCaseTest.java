package com.event_hub.ms_user.application.usecases.users;

import com.event_hub.ms_user.application.usecases.SaveUserUseCase;
import com.event_hub.ms_user.domain.entity.User;
import com.event_hub.ms_user.domain.enums.Roles;
import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.infra.queue.producer.UserMsProducer;
import com.event_hub.ms_user.infra.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveUserUseCaseTest {

    @Mock
    UserRepository repository;
    @Mock
    UserMsProducer producer;
    @InjectMocks
    SaveUserUseCase saveUserUseCase;

    @Test
    @DisplayName("Should save user and send confirmation email")
    void case01() {
        UserRequest request = new UserRequest("John Doe", "john@event-hub.com", "123456");
        saveUserUseCase.execute(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userCaptor.capture());
        verify(producer, times(1)).sendMessage(
                any()
        );
        assertEquals(Roles.ORGANIZER, userCaptor.getValue().getRoles());
    }

    @Test
    @DisplayName("Should send confirmation email after saving user")
    void shouldSaveAdminUser() {
        UserRequest request = new UserRequest("Jane Doe", "jane@event-hub.com.br", "123456");
        saveUserUseCase.execute(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userCaptor.capture());
        verify(producer, times(1)).sendMessage(
                any()
        );
        assertEquals(Roles.ADMIN, userCaptor.getValue().getRoles());
    }

    @Test
    @DisplayName("Should save generic user when email does not match specific domains")
    void shouldSaveGenericUser() {
        UserRequest request = new UserRequest("Generic User", "user@gmail.com", "123456");
        saveUserUseCase.execute(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userCaptor.capture());
        verify(producer, times(1)).sendMessage(
                any()
        );
        assertEquals(Roles.USER, userCaptor.getValue().getRoles());
    }

    @Test
    @DisplayName("Should send confirmation email with correct data")
    void shouldThrowExceptionWhenRepositoryFails() {
        UserRequest request = new UserRequest("John Doe", "john@event-hub.com", "123456");
        doThrow(new RuntimeException("Database error")).when(repository).save(any(User.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> saveUserUseCase.execute(request));
        assertEquals("Database error", exception.getMessage());
    }

}