package com.practice.domain.dto;

import com.practice.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NotNull
public record UserEditDto(
        @Email
        @Size(max = 128)
        String email,

        @Size(max = 32)
        String username,

        @Size(min = 16, max = 256)
        String password,

        @Size(max = 64)
        String firstname,

        @Size(max = 64)
        String lastname,

        Role role
) {
}
