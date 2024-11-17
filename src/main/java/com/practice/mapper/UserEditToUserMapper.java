package com.practice.mapper;

import com.practice.domain.dto.UserEditDto;
import com.practice.domain.entity.User;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserEditToUserMapper implements Mapper<UserEditDto, User> {
    @Override
    public User map(UserEditDto from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User map(UserEditDto from, User to) {
        to.setUsername(isNull(from.username()) ? to.getUsername() : from.username());
        to.setPassword(isNull(from.password()) ? to.getPassword() : from.password());
        to.setEmail(isNull(from.email()) ? to.getEmail() : from.email());
        to.setFirstname(isNull(from.firstname()) ? to.getFirstname() : from.firstname());
        to.setLastname(isNull(from.lastname()) ? to.getLastname() : from.lastname());
        to.setRole(isNull(from.role()) ? to.getRole() : from.role());
        return to;
    }
}
