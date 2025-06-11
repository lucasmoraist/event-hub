package com.event_hub.ms_user.application.usecases;

import com.event_hub.ms_user.infra.controller.request.UserRequest;
import com.event_hub.ms_user.domain.entity.User;
import com.event_hub.ms_user.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepository repository;
    private final GetUserById getUserById;

    public void execute(String id, UserRequest request) {
        log.debug("Updating user with id {}", id);
        User user = this.getUserById.execute(id);

        user.updateUser(request);
        this.repository.save(user);
        log.debug("User with id {} updated successfully", id);
    }

}
