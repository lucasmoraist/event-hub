package com.event_hub.ms_mail_sender.exceptions;

public class EmailException extends RuntimeException {

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

}
