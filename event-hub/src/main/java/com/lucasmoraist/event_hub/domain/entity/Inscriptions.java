package com.lucasmoraist.event_hub.domain.entity;

import com.lucasmoraist.event_hub.domain.enums.StatusInscriptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

}
