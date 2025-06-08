package com.lucasmoraist.event_hub.domain.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventsRequest(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Date is required")
        LocalDate date,
        @NotNull(message = "Time is required")
        LocalTime time,
        @NotBlank(message = "Location is required")
        String location,
        @NotNull(message = "Capacity is required")
        @Min(value = 1, message = "Capacity must be at least 1")
        int capacity,
        @NotBlank(message = "Created by is required")
        String createdBy
) {

}
