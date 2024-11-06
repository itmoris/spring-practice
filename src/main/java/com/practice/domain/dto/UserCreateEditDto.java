package com.practice.domain.dto;

public record UserCreateEditDto(
        String email,
        String username,
        String firstname,
        String lastname,
        String password,
        String role
) {
}
