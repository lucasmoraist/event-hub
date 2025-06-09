package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.exception.PasswordException;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @Mock
    UserRepository repository;
    @Mock
    GetUserById getUserById;
    @InjectMocks
    DeleteUserUseCase deleteUserUseCase;

    @Test
    @DisplayName("Should delete user successfully")
    void case01() {
        String userId = "1234567890";
        UserRequest request = UserRequest.builder()
                .name("John Doe")
                .email("john@doe.com")
                .password("1234567890")
                .build();
        User user = new User(request);
        user.setId(userId);

        when(getUserById.execute(userId)).thenReturn(user);

        deleteUserUseCase.execute(userId, request.password());

        verify(getUserById, times(1))
                .execute(userId);
        verify(repository, times(1))
                .delete(user);
    }

    @Test
    @DisplayName("Should throw PasswordException when password does not match")
    void case02() {
        String userId = "1234567890";
        UserRequest request = UserRequest.builder()
                .name("John Doe")
                .email("john@doe.com")
                .password("1234567890")
                .build();
        User user = new User(request);
        user.setId(userId);

        when(getUserById.execute(userId)).thenReturn(user);

        assertThrows(PasswordException.class,
                () -> deleteUserUseCase.execute(userId, "wrongPassword"));
        verify(getUserById, times(1))
                .execute(userId);
    }

}