package com.practice.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RestException {
    public UserAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "User already exists");
    }
}
