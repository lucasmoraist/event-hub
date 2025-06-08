package com.lucasmoraist.event_hub.infra.controller;

import com.lucasmoraist.event_hub.domain.response.InscriptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/inscriptions")
@Tag(name = "Inscriptions", description = "Operations related to event inscriptions management")
public interface InscriptionsController {
    @Operation(summary = "Subscribe a user to an event", description = "Allows a user to subscribe to an event by providing their user ID and the event ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription successful"),
    })
    @PostMapping("/subscribe/{userId}/{eventId}")
    ResponseEntity<InscriptionResponse> subscribe(@PathVariable String userId, @PathVariable String eventId);

    @Operation(summary = "Confirm an inscription", description = "Confirms an inscription for an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription confirmed successfully"),
    })
    @PatchMapping("/confirm/{inscriptionId}")
    ResponseEntity<InscriptionResponse> confirm(@PathVariable String inscriptionId);

    @Operation(summary = "Cancel an inscription", description = "Cancels an existing inscription for an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription cancelled successfully"),
    })
    @PatchMapping("/cancel/{inscriptionId}")
    ResponseEntity<InscriptionResponse> cancel(@PathVariable String inscriptionId);

    @Operation(summary = "Find inscriptions by user ID", description = "Retrieves a list of inscriptions for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of inscriptions retrieved successfully"),
    })
    @GetMapping("/user/{userId}")
    ResponseEntity<List<InscriptionResponse>> findByUserId(@PathVariable String userId);

    @Operation(summary = "Find inscriptions by event ID", description = "Retrieves a list of inscriptions for a specific event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of inscriptions retrieved successfully"),
    })
    @GetMapping("/event/{eventId}")
    ResponseEntity<List<InscriptionResponse>> findByEventId(@PathVariable String eventId);

    @Operation(summary = "List waiting list for an event", description = "Retrieves the waiting list for a specific event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waiting list retrieved successfully"),
    })
    @GetMapping("/waiting-list/{eventId}")
    ResponseEntity<List<InscriptionResponse>> listWaitingList(@PathVariable String eventId);

    @Operation(summary = "Check in an inscription", description = "Marks an inscription as checked in for an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription checked in successfully"),
    })
    @PatchMapping("/check-in/{inscriptionId}")
    ResponseEntity<Void> checkIn(@PathVariable String inscriptionId);

}
