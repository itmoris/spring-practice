package com.practice.domain.dto;

public record UserReadDto(
        Long id,
        String email,
        String username,
        String firstname,
        String lastname,
        String role
) {
}
