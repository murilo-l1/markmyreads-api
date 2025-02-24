package com.server.markmyreads.controller;

import com.server.markmyreads.domain.projection.ErrorDetails;
import com.server.markmyreads.handler.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class CommonController {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {

        return handleBadRequestException(e, request, e.getMessage());
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleHibernatConstraintViolationException(org.hibernate.exception.ConstraintViolationException e, HttpServletRequest request) {

        final String message = e.getConstraintName() + ": " + e.getMessage();

        return handleBadRequestException(e, request, message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {

        return handleBadRequestException(e, request, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorDetails> handleMehotArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

        final String message = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse(e.getMessage());


        return handleBadRequestException(e, request, message);
    }

    private ResponseEntity<ErrorDetails> handleBadRequestException(Exception e, HttpServletRequest request, String message) {

        log.error("{} - {}", e.getClass().getSimpleName(), message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDetails.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(message)
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorDetails> handleCommonException(CommonException e, HttpServletRequest request) {

        log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());

        return ResponseEntity.status(e.getStatus())
                .body(ErrorDetails.builder()
                        .status(e.getStatus().value())
                        .error(e.getStatus().getReasonPhrase())
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {

        log.error("Failed to parse request body at '{}'. Cause: {}", request.getRequestURI(), e.getMostSpecificCause().getMessage());

        return ResponseEntity.badRequest().body(
                 ErrorDetails.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message("Unable to process request body")
                        .path(request.getRequestURI())
                        .build());
    }

}
