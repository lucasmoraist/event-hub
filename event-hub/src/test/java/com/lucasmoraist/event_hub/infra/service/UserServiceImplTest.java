package com.lucasmoraist.event_hub.infra.service;

import com.lucasmoraist.event_hub.infra.service.impl.UserServiceImpl;
import com.lucasmoraist.event_hub.application.usecases.users.DeleteUserUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.FindUserByEmailUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.FindUserByIdUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.ListUsersUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.SaveUserUseCase;
import com.lucasmoraist.event_hub.application.usecases.users.UpdateUserUseCase;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.enums.Roles;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;
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
class UserServiceImplTest {

    @Mock
    DeleteUserUseCase deleteUserUseCase;
    @Mock
    FindUserByEmailUseCase findUserByEmailUseCase;
    @Mock
    FindUserByIdUseCase findUserByIdUseCase;
    @Mock
    ListUsersUseCase listUsersUseCase;
    @Mock
    SaveUserUseCase saveUserUseCase;
    @Mock
    UpdateUserUseCase updateUserUseCase;
    @InjectMocks
    UserServiceImpl userService;

    String email;
    String id;
    UserRequest userRequest;
    UserResponse userResponse;

    @BeforeEach
    void setUp() {
        email = "john@doe.com";
        id = "12345";
        userRequest = UserRequest.builder()
                .name("John Doe")
                .email(email)
                .password("securePassword123")
                .build();
        User user = new User(userRequest);
        user.setRoles(Roles.USER);
        userResponse = new UserResponse(user);
    }

    @Test
    @DisplayName("Should save a user successfully")
    void case01() {
        userService.saveUser(userRequest);

        assertAll(
                () -> assertNotNull(userRequest),
                () -> verify(saveUserUseCase, times(1))
                        .execute(userRequest)
        );
    }

    @Test
    @DisplayName("Should find all users successfully")
    void case02() {
        when(listUsersUseCase.execute()).thenReturn(List.of(userResponse));

        var users = userService.findAllUsers();

        assertAll(
                () -> assertNotNull(users),
                () -> assertFalse(users.isEmpty()),
                () -> assertEquals(1, users.size()),
                () -> verify(listUsersUseCase, times(1))
                        .execute()
        );
    }

    @Test
    @DisplayName("Should find a user by ID successfully")
    void case03() {
        when(findUserByIdUseCase.execute(id)).thenReturn(userResponse);

        var user = userService.findById(id);

        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(userResponse, user),
                () -> assertEquals(userRequest.name(), user.name()),
                () -> assertEquals(userRequest.email(), user.email()),
                () -> verify(findUserByIdUseCase, times(1))
                        .execute(id)
        );
    }

    @Test
    @DisplayName("Should find a user by email successfully")
    void case04() {
        when(findUserByEmailUseCase.execute(email)).thenReturn(userResponse);

        var user = userService.findByEmail(email);

        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(userResponse, user),
                () -> assertEquals(userRequest.name(), user.name()),
                () -> assertEquals(userRequest.email(), user.email()),
                () -> verify(findUserByEmailUseCase, times(1))
                        .execute(email)
        );
    }

    @Test
    @DisplayName("Should update a user successfully")
    void case05() {
        userService.updateUser(id, userRequest);

        assertAll(
                () -> assertNotNull(userRequest),
                () -> verify(updateUserUseCase, times(1))
                        .execute(id, userRequest)
        );
    }

    @Test
    @DisplayName("Should delete a user successfully")
    void case06() {
        userService.deleteUser(id, userRequest.password());

        assertAll(
                () -> assertNotNull(userRequest.password()),
                () -> verify(deleteUserUseCase, times(1))
                        .execute(id, userRequest.password())
        );
    }

}