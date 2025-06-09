package com.lucasmoraist.event_hub.application.usecases.users;

import com.lucasmoraist.event_hub.domain.response.UserResponse;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListUsersUseCase {

    private final UserRepository repository;

    public List<UserResponse> execute() {
        log.info("Fetching all users");
        return this.repository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

}
