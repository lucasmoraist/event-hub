package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.enums.Roles;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.model.EmailData;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.infra.queue.producer.EventHubProducer;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.lucasmoraist.event_hub.infra.queue.producer.EventHubProducer.TO_CONFIRM_EMAIL;
import static org.springframework.integration.support.MessageBuilder.withPayload;

@Log4j2
@Service
@RequiredArgsConstructor
public class SaveUserUseCase {

    private static final String ORGANIZER_EMAIL_DOMAIN = "@event-hub.com";
    private static final String ADMIN_EMAIL_DOMAIN = "@event-hub.com.br";

    private final UserRepository repository;
    private final EventHubProducer producer;

    public void execute(UserRequest request) {
        User user = new User(request);
        Roles role = this.validateRole(request);
        user.setRoles(role);

        this.repository.save(user);
        log.debug("User with email {} created successfully", request.email());

        this.sendMessage(user);
        log.debug("Confirmation email sent to {}", user.getEmail());
    }

    private void sendMessage(User user) {
        EmailData data = new EmailData(
                user.getEmail(),
                user.getName()
        );
        producer.sendMessage(withPayload(data)
                .build(),
                TO_CONFIRM_EMAIL
        );
    }

    private Roles validateRole(UserRequest request) {
        if (request.email().endsWith(ORGANIZER_EMAIL_DOMAIN)) {
            return Roles.ORGANIZER;
        } else if (request.email().endsWith(ADMIN_EMAIL_DOMAIN)) {
            return Roles.ADMIN;
        } else {
            return Roles.USER;
        }
    }

}
