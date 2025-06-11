package com.event_hub.ms_events.domain.entity;

import com.event_hub.ms_events.domain.exception.InvalidFieldException;
import com.event_hub.ms_events.infra.controller.request.EventsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Log4j2
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class Events {

    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private int capacity;
    private String createdBy;
    private LocalDateTime createdAt;

    public Events(EventsRequest request) {
        this.title = request.title();
        this.description = request.description();
        this.date = request.date();
        this.time = request.time();
        this.location = request.location();
        this.capacity = request.capacity();
        this.createdBy = request.createdBy();
        this.createdAt = LocalDateTime.now();
    }

    public void updateEvent(EventsRequest request) {
        if (request.title() != null) {
            this.title = request.title();
        }
        if (request.description() != null) {
            this.description = request.description();
        }
        if (request.date() != null) {
            this.date = request.date();
        }
        if (request.time() != null) {
            this.time = request.time();
        }
        if (request.location() != null) {
            log.error("Location cannot be updated");
            throw new InvalidFieldException("Location cannot be updated");
        }
        if (request.capacity() < 0) {
            log.error("Capacity cannot be negative");
            throw new InvalidFieldException("Capacity cannot be negative");
        }
        if (request.createdBy() != null) {
            log.error("Created by cannot be updated");
            throw new InvalidFieldException("Created by cannot be updated");
        }
    }

}
