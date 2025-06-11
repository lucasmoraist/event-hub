package com.event_hub.ms_events.domain.exception;

public class SameEventException extends RuntimeException {

    public SameEventException(String message) {
        super(message);
    }

}
