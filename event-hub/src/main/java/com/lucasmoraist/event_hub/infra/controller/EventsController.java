package com.lucasmoraist.event_hub.infra.controller;

import com.lucasmoraist.event_hub.domain.request.EventsRequest;
import com.lucasmoraist.event_hub.domain.response.EventsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/events")
@Tag(name = "Events", description = "Operations related to events management")
public interface EventsController {

    @Operation(summary = "Create a new event", description = "Creates a new event with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
    })
    @PostMapping
    ResponseEntity<Void> createEvent(@Valid @RequestBody EventsRequest request);

    @Operation(summary = "Find event by ID", description = "Retrieves the details of an event by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<EventsResponse> findById(@PathVariable String id);

    @Operation(summary = "Update an event", description = "Updates the details of an existing event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PatchMapping("/{id}")
    ResponseEntity<Void> updateEvent(@PathVariable String id, @RequestBody EventsRequest request);

    @Operation(summary = "Delete an event", description = "Deletes an event by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}/delete")
    ResponseEntity<Void> deleteEvent(@PathVariable String id);

    @Operation(summary = "Find all events", description = "Retrieves a list of all events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully")
    })
    @GetMapping("/all")
    ResponseEntity<List<EventsResponse>> findAll();

    @Operation(summary = "List upcoming events", description = "Retrieves a list of all upcoming events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upcoming events retrieved successfully")
    })
    @GetMapping("/upcoming")
    ResponseEntity<List<EventsResponse>> listUpComing();

    @Operation(summary = "List available events", description = "Retrieves a list of all available events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available events retrieved successfully")
    })
    @GetMapping("/available")
    ResponseEntity<List<EventsResponse>> listAvailableEvents();

}
