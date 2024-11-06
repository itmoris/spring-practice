package com.practice.mapper;

import com.practice.domain.dto.UserReadDto;
import com.practice.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User user) {
        return new UserReadDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole().name()
        );
    }
}
