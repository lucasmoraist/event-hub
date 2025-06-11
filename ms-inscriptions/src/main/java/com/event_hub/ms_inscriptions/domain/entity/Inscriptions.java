package com.event_hub.ms_inscriptions.domain.entity;

import com.event_hub.ms_inscriptions.domain.enums.StatusInscriptions;
import com.event_hub.ms_inscriptions.domain.exception.StatusException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Log4j2
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inscriptions")
public class Inscriptions {

    @Id
    private String id;
    private String userId;
    private String eventId;
    private StatusInscriptions status;
    private LocalDateTime subscribedAt;

    public Inscriptions(String userId, String eventId) {
        this.userId = userId;
        this.eventId = eventId;
        this.status = StatusInscriptions.PENDING;
        this.subscribedAt = LocalDateTime.now();
    }

    public void confirm(StatusInscriptions status) {
        if (StatusInscriptions.PENDING.equals(status)) {
            this.status = StatusInscriptions.CONFIRMED;
        } else {
            log.error("Cannot confirm inscription with status: {}", status);
            throw new StatusException("Cannot confirm inscription with status: " + status);
        }
    }

    public void cancel(StatusInscriptions status) {
        if (StatusInscriptions.PENDING.equals(status) || StatusInscriptions.CONFIRMED.equals(status)) {
            this.status = StatusInscriptions.CANCELLED;
        } else {
            log.error("Cannot cancel inscription with status: {}", status);
            throw new StatusException("Cannot cancel inscription with status: " + status);
        }
    }

    public void checkIn(StatusInscriptions status) {
        if (StatusInscriptions.CONFIRMED.equals(status)) {
            this.status = StatusInscriptions.CHECKED_IN;
        } else {
            log.error("Cannot check-in inscription with status: {}", status);
            throw new StatusException("Cannot check-in inscription with status: " + status);
        }
    }

}
