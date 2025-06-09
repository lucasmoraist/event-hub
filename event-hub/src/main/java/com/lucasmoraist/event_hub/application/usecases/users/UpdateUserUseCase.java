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
    private final GetUserById getUserById;

    public void execute(String id, UserRequest request) {
        log.debug("Updating user with id {}", id);
        User user = this.getUserById.execute(id);

        user.updateUser(request);
        this.repository.save(user);
        log.debug("User with id {} updated successfully", id);
    }

}
