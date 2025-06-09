package com.lucasmoraist.event_hub.domain.exception;

public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException(String message) {
        super(message);
    }

}
