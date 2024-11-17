package com.practice.domain.dto;

import com.practice.domain.enums.Role;

public record UserFilterDto(
        String email,
        String username,
        String firstname,
        String lastname,
        Role role
) {

}
