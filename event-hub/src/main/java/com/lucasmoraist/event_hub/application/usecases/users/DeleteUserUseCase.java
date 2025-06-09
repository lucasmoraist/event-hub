package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserRepository repository;

    public void execute(String id, String password) {
        log.debug("Deleting user with id {}", id);
        User user = this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new RuntimeException("User not found");
                });

        if (!user.getPassword().equals(password)) {
            log.error("Password does not match for user with id {}", id);
            throw new RuntimeException("Password does not match");
        }

        this.repository.delete(user);
        log.debug("User with id {} deleted successfully", id);
    }

}
