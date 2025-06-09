package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.enums.Roles;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListUsersUseCaseTest {

    @Mock
    UserRepository repository;
    @InjectMocks
    ListUsersUseCase listUsersUseCase;

    @Test
    @DisplayName("Should return a list of users")
    void shouldReturnListOfUsers() {
        String userId = "1234567890";
        UserRequest request = UserRequest.builder()
                .name("John Doe")
                .email("john@doe.com")
                .password("1234567890")
                .build();
        User user = new User(request);
        user.setId(userId);
        user.setRoles(Roles.USER);

        when(repository.findAll()).thenReturn(List.of(user));

        var result = listUsersUseCase.execute();

        assertAll("Result",
                () -> assertNotNull(result),
                () -> assertFalse(result.isEmpty()),
                () -> assertEquals(1, result.size()),
                () -> assertEquals(userId, result.get(0).id()),
                () -> assertEquals(user.getName(), result.get(0).name()),
                () -> assertEquals(user.getEmail(), result.get(0).email()),
                () -> assertEquals(Roles.USER.name(), result.get(0).roles())
        );
    }

}