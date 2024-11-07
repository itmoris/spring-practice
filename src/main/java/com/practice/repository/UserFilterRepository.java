package com.practice.repository;

import com.practice.domain.dto.UserFilterDto;
import com.practice.domain.entity.User;

import java.util.List;

public interface UserFilterRepository {
    List<User> findAllByFilter(UserFilterDto filterDto);
}
