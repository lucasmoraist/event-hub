package com.lucasmoraist.event_hub.domain.response;

import com.lucasmoraist.event_hub.domain.entity.Events;

public record EventsResponse(
        String id,
        String title,
        String description,
        String date,
        String time,
        String location,
        int capacity,
        String createdBy
) {

    public EventsResponse(Events events) {
        this(
                events.getId(),
                events.getTitle(),
                events.getDescription(),
                events.getDate().toString(),
                events.getTime().toString(),
                events.getLocation(),
                events.getCapacity(),
                events.getCreatedBy()
        );
    }

}
