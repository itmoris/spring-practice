package com.practice.integration.repository;

import com.practice.domain.entity.User;
import com.practice.integration.IntegrationTest;
import com.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@RequiredArgsConstructor
class UserRepositoryIT {
    private final UserRepository userRepository;

    @Test
    void givenEmptyTable_whenFindAll_thenReturnEmptyList() {
        List<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    void givenEmptyTable_whenFindById_thenReturnEmptyOptional() {
        Optional<User> byId = userRepository.findById(1L);
        assertThat(byId).isEmpty();
    }

    @Test
    void givenEmptyTable_whenSaveAndFindById_thenReturnSavedUser() {

    }
}
