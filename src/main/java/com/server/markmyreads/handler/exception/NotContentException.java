package com.server.markmyreads.handler.exception;

import org.springframework.http.HttpStatus;

public class NotContentException extends CommonException {

    public NotContentException(final String message) {
        super(HttpStatus.NO_CONTENT, message);
    }
}
