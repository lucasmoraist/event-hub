package com.lucasmoraist.event_hub.domain.entity;

import com.lucasmoraist.event_hub.domain.enums.Roles;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private UUID id;
    private String name;
    // TODO: Criar validação para email organizacional, quando a role for ORGANIZER o email deve ser @event-hub.com e para
    //  ADMIN o email deve ser @event-hub.com.br
    @Indexed(unique = true)
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private Roles roles;

    public User(UserRequest request) {
        this.name = request.name();
        this.email = request.email();
        this.password = request.password();
        this.createdAt = LocalDateTime.now();
        this.roles = request.roles();
    }

    public void updateUser(UserRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.email() != null) {
            this.email = request.email();
        }
        if (request.password() != null) {
            this.password = request.password();
        }
        if (request.roles() != null) {
            log.error("Roles cannot be updated");
            throw new RuntimeException("Roles cannot be updated");
        }
    }

}
