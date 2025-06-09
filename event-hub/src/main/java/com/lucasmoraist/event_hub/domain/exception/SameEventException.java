package com.lucasmoraist.event_hub.domain.exception;

public class SameEventException extends RuntimeException {

    public SameEventException(String message) {
        super(message);
    }

}
