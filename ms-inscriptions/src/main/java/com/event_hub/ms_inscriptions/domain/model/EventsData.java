package com.event_hub.ms_inscriptions.domain.model;

import lombok.Builder;

@Builder
public record EventsData(
        String id,
        String title,
        String description,
        String date,
        String time,
        String location,
        int capacity,
        String createdBy
) {

}
