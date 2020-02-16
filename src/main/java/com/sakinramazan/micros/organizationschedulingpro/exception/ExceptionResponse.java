package com.sakinramazan.micros.organizationschedulingpro.exception;

import lombok.Value;

import java.util.Date;

@Value
public final class ExceptionResponse {
    private final Date timestamp;
    private final String message;
    private final String detail;
}
