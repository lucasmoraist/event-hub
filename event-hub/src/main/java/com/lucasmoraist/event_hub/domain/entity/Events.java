package com.lucasmoraist.event_hub.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class Events {


    // Antes de salvar no banco devo validar se já existe um evento na mesma data e hora na mesma localização e se o evento tem
    // o mesmo nome
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

}
