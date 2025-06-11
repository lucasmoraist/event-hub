package com.event_hub.ms_user.application.usecases;

import com.event_hub.ms_user.infra.controller.response.UserResponse;
import com.event_hub.ms_user.domain.exception.NotFoundException;
import com.event_hub.ms_user.infra.repository.UserRepository;
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
                    return new NotFoundException("User not found");
                });
    }

}
