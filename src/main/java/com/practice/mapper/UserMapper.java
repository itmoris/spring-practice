package com.practice.mapper;

import com.practice.domain.dto.UserCreateEditDto;
import com.practice.domain.entity.User;
import com.practice.domain.enums.Role;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper implements Mapper<UserCreateEditDto, User> {
    @Override
    public User map(UserCreateEditDto from) {
        return copy(from, new User());
    }

    @Override
    public User map(UserCreateEditDto from, User to) {
        return copy(from, to);
    }

    private User copy(UserCreateEditDto from, User user) {
        Role role = Optional.ofNullable(from.role())
                .map(Role::valueOf)
                .orElse(Role.USER);

        user.setEmail(from.email());
        user.setUsername(from.username());
        user.setPassword(from.password());
        user.setFirstname(from.firstname());
        user.setLastname(from.lastname());
        user.setRole(role);

        return user;
    }
}
