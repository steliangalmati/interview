package com.interview.resource.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptionGeneric(Exception ex, WebRequest request) {
        // TODO more custom handlers to be added, decide what to log and what not
        log.error("Unknown exception occurred: {}", ex.getMessage(), ex);

        return handleExceptionInternal(
                ex,
                ex.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                ex.getMessage(),
                null,
                HttpStatus.UNAUTHORIZED,
                request);
    }
}
