package com.event_hub.ms_user.application.usecases.users;

import com.event_hub.ms_user.application.usecases.FindUserByEmailUseCase;
import com.event_hub.ms_user.domain.entity.User;
import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.infra.repository.UserRepository;
import com.event_hub.ms_user.domain.enums.Roles;
import com.event_hub.ms_user.domain.exception.NotFoundException;
import com.event_hub.ms_user.infra.controller.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByEmailUseCaseTest {

    @Mock
    UserRepository repository;
    @InjectMocks
    FindUserByEmailUseCase findUserByEmailUseCase;

    @Test
    @DisplayName("Should find user by email successfully")
    void case01() {
        String userId = "1234567890";
        UserRequest request = UserRequest.builder()
                .name("John Doe")
                .email("john@doe.com")
                .password("1234567890")
                .build();
        User user = new User(request);
        user.setId(userId);
        user.setRoles(Roles.USER);

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserResponse result = findUserByEmailUseCase.execute(user.getEmail());

        assertAll("Response",
                () -> assertNotNull(result),
                () -> assertEquals(userId, result.id()),
                () -> assertEquals(user.getName(), result.name()),
                () -> assertEquals(user.getEmail(), result.email()),
                () -> assertEquals(Roles.USER.name(), result.roles())
        );
        verify(repository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not found by email")
    void case02() {
        String email = "nonexistent@domain.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> findUserByEmailUseCase.execute(email));

        verify(repository, times(1)).findByEmail(email);
    }

}