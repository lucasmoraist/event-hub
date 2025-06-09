package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.request.UserRequest;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository repository;

    public void execute(String id, UserRequest request) {
        log.debug("Updating user with id {}", id);
        User user = this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new RuntimeException("User not found");
                });

        user.updateUser(request);
        this.repository.save(user);
        log.debug("User with id {} updated successfully", id);
    }

}
