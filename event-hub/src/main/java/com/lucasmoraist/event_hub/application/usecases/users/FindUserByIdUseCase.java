package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.response.UserResponse;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {

    private final UserRepository repository;

    public UserResponse execute(String id) {
        log.debug("Fetching user with id {}", id);
        return this.repository.findById(id)
                .map(UserResponse::new)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new RuntimeException("User not found");
                });
    }

}
