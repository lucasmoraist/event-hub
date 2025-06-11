package com.event_hub.ms_user.application.usecases.users;

import com.event_hub.ms_user.application.usecases.GetUserById;
import com.event_hub.ms_user.application.usecases.UpdateUserUseCase;
import com.event_hub.ms_user.domain.entity.User;
import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.infra.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    UserRepository repository;
    @Mock
    GetUserById getUserById;
    @InjectMocks
    UpdateUserUseCase updateUserUseCase;

    @Test
    @DisplayName("Should update user successfully")
    void case01() {
        String userId = "123";
        UserRequest request = new UserRequest("newName", "newEmail", "newPassword");
        User user = new User(request);
        user.setId(userId);

        when(getUserById.execute(userId)).thenReturn(user);

        updateUserUseCase.execute(userId, request);

        verify(repository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void case02() {
        String userId = "123";
        UserRequest request = new UserRequest("newName", "newEmail", "newPassword");

        when(getUserById.execute(userId)).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> updateUserUseCase.execute(userId, request));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should update user with null fields in request")
    void case03() {
        String userId = "123";
        UserRequest request = new UserRequest("newName", "newEmail", "newPassword");
        User user = new User(request);
        user.setId(userId);

        when(getUserById.execute(userId)).thenReturn(user);
        doThrow(new RuntimeException("Save failed")).when(repository).save(user);

        assertThrows(RuntimeException.class, () -> updateUserUseCase.execute(userId, request));
        verify(repository).save(user);
    }

}