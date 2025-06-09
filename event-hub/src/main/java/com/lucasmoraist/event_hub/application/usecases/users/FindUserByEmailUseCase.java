package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.response.UserResponse;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class FindUserByEmailUseCase {

    private final UserRepository repository;

    public UserResponse execute(String email) {
        log.debug("Fetching user with email {}", email);
        return this.repository.findByEmail(email)
                .map(UserResponse::new)
                .orElseThrow(() -> {
                    log.error("User with email {} not found", email);
                    return new RuntimeException("User not found");
                });
    }

}
