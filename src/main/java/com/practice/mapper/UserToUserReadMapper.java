package com.practice.mapper;

import com.practice.domain.dto.UserReadDto;
import com.practice.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User from) {
        return new UserReadDto(
                from.getId(),
                from.getEmail(),
                from.getUsername(),
                from.getFirstname(),
                from.getLastname(),
                from.getRole().name()
        );
    }
}
