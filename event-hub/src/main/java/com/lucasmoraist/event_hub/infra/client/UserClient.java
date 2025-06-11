package com.lucasmoraist.event_hub.infra.client;

import com.lucasmoraist.event_hub.domain.request.UserRequest;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-user", url = "${microservice.endpoint.user}")
public interface UserClient {

    @PostMapping
    Response saveUser(@RequestBody UserRequest request);

    @GetMapping
    Response findAllUsers();

    @GetMapping("/{id}")
    Response findById(@PathVariable String id);

    @GetMapping("/email")
    Response findByEmail(@RequestParam String email);

    @PatchMapping("/{id}")
    Response updateUser(@PathVariable String id, @RequestBody UserRequest request);

    @DeleteMapping("/{id}")
    Response deleteUser(@PathVariable String id, @RequestParam String password);
}
