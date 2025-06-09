package com.lucasmoraist.event_hub.infra.controller;

import com.lucasmoraist.event_hub.infra.service.InscriptionsService;
import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InscriptionsControllerImplTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    InscriptionsService inscriptionsService;
    InscriptionResponse response;

    @BeforeEach
    void setUp() {
        response = new InscriptionResponse(
                "123456",
                "John Doe",
                "john@doe.com",
                "Event Name",
                LocalDate.of(2023, 10, 1).toString(),
                LocalTime.of(10, 0).toString(),
                "Sample Location",
                StatusInscriptions.PENDING.name()
        );
    }

    @Test
    @DisplayName("Test createInscription with valid data")
    void case01() throws Exception {
        when(inscriptionsService.subscribe("user1", "event1")).thenReturn(response);

        mockMvc.perform(post("/inscriptions/subscribe/user1/event1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(inscriptionsService, times(1)).subscribe("user1", "event1");
    }

    @Test
    @DisplayName("Test createInscription with invalid data")
    void case02() throws Exception {
        when(inscriptionsService.confirm("inscription1")).thenReturn(response);

        mockMvc.perform(patch("/inscriptions/confirm/inscription1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(inscriptionsService, times(1)).confirm("inscription1");
    }

    @Test
    @DisplayName("Test cancelInscription with valid data")
    void case03() throws Exception {
        when(inscriptionsService.cancel("inscription1")).thenReturn(response);

        mockMvc.perform(patch("/inscriptions/cancel/inscription1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(inscriptionsService, times(1)).cancel("inscription1");
    }

    @Test
    @DisplayName("Test findById with valid ID")
    void case04() throws Exception {
        when(inscriptionsService.findByUserId("user1")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/inscriptions/user/user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(inscriptionsService, times(1)).findByUserId("user1");
    }

    @Test
    @DisplayName("Test findByEventId with valid ID")
    void case05() throws Exception {
        when(inscriptionsService.findByEventId("event1")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/inscriptions/event/event1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(inscriptionsService, times(1)).findByEventId("event1");
    }

    @Test
    @DisplayName("Test listWaitingList with valid event ID")
    void case06() throws Exception {
        when(inscriptionsService.listWaitingList("event1")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/inscriptions/waiting-list/event1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(inscriptionsService, times(1)).listWaitingList("event1");
    }

    @Test
    @DisplayName("Test checkIn with valid inscription ID")
    void case07() throws Exception {
        doNothing().when(inscriptionsService).checkIn("inscription1");

        mockMvc.perform(patch("/inscriptions/check-in/inscription1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(inscriptionsService, times(1)).checkIn("inscription1");
    }

}