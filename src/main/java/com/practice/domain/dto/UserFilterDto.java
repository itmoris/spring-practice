package com.practice.domain.dto;

import com.practice.domain.enums.Role;

public record UserFilterDto(
        String username,
        Role role
) {
}
