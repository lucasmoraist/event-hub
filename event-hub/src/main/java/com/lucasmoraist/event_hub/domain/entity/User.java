package com.lucasmoraist.event_hub.domain.entity;

import com.lucasmoraist.event_hub.domain.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private UUID id;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private Roles roles;

}
