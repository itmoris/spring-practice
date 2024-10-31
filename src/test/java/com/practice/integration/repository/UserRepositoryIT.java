package com.practice.integration.repository;

import com.practice.domain.entity.User;
import com.practice.integration.IntegrationTest;
import com.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

@IntegrationTest
@RequiredArgsConstructor
public class UserRepositoryIT {
    private final UserRepository userRepository;

    @Test
    void getAll() {
        List<User> users = userRepository.findAll();
        System.out.println(users);
    }
}
