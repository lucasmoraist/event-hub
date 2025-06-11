package com.event_hub.ms_events.infra.controller;

import com.event_hub.ms_events.infra.controller.impl.EventsControllerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.event_hub.ms_events.application.service.EventsService;
import com.event_hub.ms_events.domain.entity.Events;
import com.event_hub.ms_events.infra.controller.request.EventsRequest;
import com.event_hub.ms_events.infra.controller.response.EventsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventsControllerImpl.class)
class EventsControllerImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EventsService service;

    @Autowired
    ObjectMapper objectMapper;

    EventsRequest request;
    EventsResponse response;

    @BeforeEach
    void setUp() {
        request = new EventsRequest(
                "Sample Event",
                "This is a sample event description.",
                LocalDate.of(2023, 10, 1),
                LocalTime.of(10, 0),
                "Sample Location",
                100,
                "Sample Organizer"
        );
        Events events = new Events(request);
        response = new EventsResponse(events);
    }

    @Test
    @DisplayName("Create Event - Valid Request")
    void case01() throws Exception {
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        verify(service).createEvent(any(EventsRequest.class));
    }

    @Test
    @DisplayName("Create Event - Invalid Request")
    void case02() throws Exception {
        when(service.findById("1")).thenReturn(response);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("Get Event by ID - Valid ID")
    void case03() throws Exception {
        mockMvc.perform(patch("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        verify(service).updateEvent(eq("1"), any(EventsRequest.class));
    }

    @Test
    @DisplayName("Get Event by ID - Invalid ID")
    void cae04() throws Exception {
        mockMvc.perform(delete("/events/1/delete"))
                .andExpect(status().isOk());
        verify(service).deleteEvent("1");
    }

    @Test
    @DisplayName("Get Event by ID - Valid ID")
    void case05() throws Exception {
        List<EventsResponse> responses = Collections.singletonList(response);
        when(service.findAll()).thenReturn(responses);

        mockMvc.perform(get("/events/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(responses)));
    }

    @Test
    @DisplayName("Get Event by ID - Invalid ID")
    void case06() throws Exception {
        List<EventsResponse> responses = Collections.singletonList(response);
        when(service.listUpComing()).thenReturn(responses);

        mockMvc.perform(get("/events/upcoming"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(responses)));
    }

    @Test
    @DisplayName("Get Event by ID - Valid ID")
    void case07() throws Exception {
        List<EventsResponse> responses = Collections.singletonList(response);
        when(service.listAvailableEvents()).thenReturn(responses);

        mockMvc.perform(get("/events/available"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(responses)));
    }

}