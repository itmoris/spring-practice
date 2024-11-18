package com.practice.exception;

import org.springframework.http.HttpStatus;

public class AvatarNotFoundException extends RestException {
    public AvatarNotFoundException() {
        super(HttpStatus.NO_CONTENT, "Avatar not found");
    }
}
