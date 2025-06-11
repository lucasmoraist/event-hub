package com.event_hub.ms_inscriptions.infra.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "events-client", url = "${microservice.endpoint.events}")
public interface EventsClient {

    @GetMapping("/{eventId}")
    Response getEventById(@PathVariable String eventId);

    @GetMapping("/all")
    Response getAllEvents();

}
