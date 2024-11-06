package com.practice.domain.dto;

public record UserReadDto(
        Long id,
        String username,
        String password,
        String email,
        String firstname,
        String lastname,
        String role
) {
}
