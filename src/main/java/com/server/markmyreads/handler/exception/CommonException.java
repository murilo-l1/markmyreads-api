package com.server.markmyreads.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException{

    private final HttpStatus status;

    private final String message;

    public CommonException(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

}