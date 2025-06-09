package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.infra.email.EmailService;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.model.EmailData;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SaveUserUseCase {

    private final UserRepository repository;
    private final EmailService emailService;

    public void execute(UserRequest request) {
        User user = new User(request);
        this.repository.save(user);
        log.debug("User with email {} created successfully", request.email());
        EmailData data = new EmailData(
                user.getName(),
                user.getEmail()
        );
        this.emailService.confirmEmail(data);
        log.debug("Confirmation email sent to {}", user.getEmail());
    }

}
