package com.practice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RestException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}
