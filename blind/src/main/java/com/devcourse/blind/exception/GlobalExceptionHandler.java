package com.devcourse.blind.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse illegalStateHandler(IllegalStateException e) {
        log.error("[illegalStateHandler] - {}", e.getMessage());
        return ErrorResponse.builder()
                .code(BAD_REQUEST.value())
                .status(BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .time(now())
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse exceptionHandler(Exception e) {
        log.error("[exceptionHandler] - {}", e.getMessage());
        return ErrorResponse.builder()
                .code(BAD_REQUEST.value())
                .status(BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .time(now())
                .build();
    }
}
