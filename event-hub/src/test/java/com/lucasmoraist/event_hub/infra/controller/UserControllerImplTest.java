package com.lucasmoraist.event_hub.infra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasmoraist.event_hub.infra.service.UserService;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.enums.Roles;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.domain.response.UserResponse;
import com.lucasmoraist.event_hub.infra.controller.impl.UserControllerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerImpl.class)
class UserControllerImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;
    
    UserRequest request;
    UserResponse response;
    
    @BeforeEach
    void setUp() {
        request = UserRequest.builder()
                .name("John")
                .email("john@example.com")
                .password("password123")
                .build();
        User user = new User(request);
        user.setId("1");
        user.setRoles(Roles.USER);
        response = new UserResponse(user);
    }

    @Test
    @DisplayName("Test saving a user")
    void case01() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(userService).saveUser(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test finding all users")
    void case02() throws Exception {
        when(userService.findAllUsers()).thenReturn(List.of(response));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));

        verify(userService).findAllUsers();
    }

    @Test
    @DisplayName("Test finding a user by ID")
    void case03() throws Exception {
        when(userService.findById("1")).thenReturn(response);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService).findById("1");
    }

    @Test
    @DisplayName("Test finding a user by email")
    void case04() throws Exception {
        when(userService.findByEmail("john@example.com")).thenReturn(response);

        mockMvc.perform(get("/users/email")
                        .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService).findByEmail("john@example.com");
    }

    @Test
    @DisplayName("Test updating a user")
    void case05() throws Exception {
        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq("1"), any(UserRequest.class));
    }

    @Test
    @DisplayName("Test deleting a user")
    void case06() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .param("password", "password123"))
                .andExpect(status().isOk());

        verify(userService).deleteUser("1", "password123");
    }
    
}