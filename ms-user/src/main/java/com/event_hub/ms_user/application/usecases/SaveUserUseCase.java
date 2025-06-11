package com.event_hub.ms_user.application.usecases;

import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.domain.entity.User;
import com.event_hub.ms_user.domain.enums.Roles;
import com.event_hub.ms_user.domain.model.ConfirmEmailData;
import com.event_hub.ms_user.infra.queue.producer.UserMsProducer;
import com.event_hub.ms_user.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static org.springframework.integration.support.MessageBuilder.withPayload;

@Log4j2
@Service
@RequiredArgsConstructor
public class SaveUserUseCase {

    private static final String ORGANIZER_EMAIL_DOMAIN = "@event-hub.com";
    private static final String ADMIN_EMAIL_DOMAIN = "@event-hub.com.br";

    private final UserRepository repository;
    private final UserMsProducer producer;

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
        ConfirmEmailData data = new ConfirmEmailData(
                user.getEmail(),
                user.getName()
        );
        producer.sendMessage(withPayload(data).build());
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
