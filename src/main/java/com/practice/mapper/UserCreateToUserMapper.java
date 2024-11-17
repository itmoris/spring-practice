package com.practice.mapper;

import com.practice.domain.dto.UserCreateDto;
import com.practice.domain.entity.User;
import com.practice.domain.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserCreateToUserMapper implements Mapper<UserCreateDto, User> {
    @Override
    public User map(UserCreateDto from) {
        User user = new User();

        user.setUsername(from.username());
        user.setEmail(from.email());
        user.setPassword(from.password());
        user.setFirstname(from.firstname());
        user.setLastname(from.lastname());
        user.setRole(Role.USER);
        return user;
    }
}
