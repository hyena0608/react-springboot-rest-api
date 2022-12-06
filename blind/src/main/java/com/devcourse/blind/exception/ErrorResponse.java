package com.devcourse.blind.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(int code, String status, String message, LocalDateTime time) {
}
