package com.practice.domain.dto;

import com.practice.validation.custom.UserInfo;
import com.practice.validation.group.CreateAction;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@UserInfo(groups = {CreateAction.class})
public record UserCreateEditDto(
        @Email
        @NotNull
        String email,

        @NotBlank
        @Size(min = 6, max = 32)
        String username,

        @NotBlank
        @Size(min = 16, max = 256)
        String password,

        String firstname,
        String lastname,
        String role
) {
}
