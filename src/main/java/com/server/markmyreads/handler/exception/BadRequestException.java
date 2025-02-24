package com.server.markmyreads.handler.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CommonException {

    public BadRequestException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}