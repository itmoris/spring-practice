package com.practice.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        @Email(regexp = "^(.+)@(\\S+)$")
        @NotBlank
        @Size(max = 128)
        String email,

        @NotBlank
        @Size(max = 32)
        String username,

        @NotBlank
        @Size(min = 16, max = 256)
        String password,

        @Size(max = 64)
        String firstname,

        @Size(max = 64)
        String lastname
) {
}
