package com.server.markmyreads.handler.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CommonException {

    public NotFoundException(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
