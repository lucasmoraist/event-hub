package com.event_hub.ms_user.application.usecases;

import com.event_hub.ms_user.domain.entity.User;
import com.event_hub.ms_user.domain.exception.PasswordException;
import com.event_hub.ms_user.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserRepository repository;
    private final GetUserById getUserById;

    public void execute(String id, String password) {
        log.debug("Deleting user with id {}", id);
        User user = this.getUserById.execute(id);

        this.validatePassword(user, password);

        this.repository.delete(user);
        log.debug("User with id {} deleted successfully", id);
    }

    private void validatePassword(User user, String password) {
        if (!user.getPassword().equals(password)) {
            log.error("Password does not match for user with id {}", user.getId());
            throw new PasswordException("Password does not match");
        }
    }

}
