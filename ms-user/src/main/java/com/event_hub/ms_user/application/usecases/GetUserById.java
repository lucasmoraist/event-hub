package com.event_hub.ms_user.application.usecases;

import com.event_hub.ms_user.domain.entity.User;
import com.event_hub.ms_user.domain.exception.NotFoundException;
import com.event_hub.ms_user.infra.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GetUserById {

    @Autowired
    private UserRepository repository;

    public User execute(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("User with id {} not found", id);
                    return new NotFoundException("User not found");
                });
    }

}
