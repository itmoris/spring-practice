package com.practice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestException extends RuntimeException {
    private final Integer status;

    public RestException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
    }
}
