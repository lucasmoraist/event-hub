package com.event_hub.ms_inscriptions.application.service;

import com.event_hub.ms_inscriptions.application.service.impl.UserServiceImpl;
import com.event_hub.ms_inscriptions.domain.model.UserData;
import com.event_hub.ms_inscriptions.infra.client.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserClient userClient;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("Should return user data when user exists")
    void case01() throws IOException {
        UserData userData = UserData.builder()
                .id("123")
                .name("John Doe")
                .email("john@example.com")
                .roles("USER")
                .build();

        when(userClient.getUserById("123")).thenReturn(mockResponse());
        when(objectMapper.readValue(any(ByteArrayInputStream.class), eq(UserData.class))).thenReturn(userData);

        UserData result = userService.getUserById("123");

        assertAll("Results",
                () -> assertNotNull(result),
                () -> assertEquals(userData.id(), result.id(), "User ID should match"),
                () -> assertEquals(userData.name(), result.name(), "User name should match"),
                () -> assertEquals(userData.email(), result.email(), "User email should match"),
                () -> assertEquals(userData.roles(), result.roles(), "User roles should match")
        );

        assertAll("Verify",
                () -> verify(userClient, times(1))
                        .getUserById(anyString()),
                () -> verify(objectMapper, times(1))
                        .readValue(any(InputStream.class), eq(UserData.class))
        );
    }

    private Response mockResponse() {
        Request mockRequest = Request.create(
                Request.HttpMethod.GET,
                "/users/123",
                Collections.emptyMap(),
                null,
                null,
                null
        );

        return Response.builder()
                .status(200)
                .request(mockRequest)
                .body(new ByteArrayInputStream("".getBytes()), null)
                .build();
    }


}