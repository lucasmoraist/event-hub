package com.lucasmoraist.event_hub.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(
        description = "Request object for creating or updating an event",
        requiredProperties = {"title", "description", "date", "time", "location", "capacity", "createdBy"},
        example = """
                {
                    "title": "Tech Conference 2023",
                    "description": "A conference about the latest in technology.",
                    "date": "2023-10-15",
                    "time": "09:00",
                    "location": "Convention Center, Cityville",
                    "capacity": 500,
                    "createdBy": "admin"
                }
                """
)
@Builder
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
