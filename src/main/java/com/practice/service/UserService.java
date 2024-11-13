package com.practice.service;

import com.practice.domain.dto.PageResponse;
import com.practice.domain.dto.PageResponse.Pagination;
import com.practice.domain.dto.UserCreateEditDto;
import com.practice.domain.dto.UserFilterDto;
import com.practice.domain.dto.UserReadDto;
import com.practice.domain.entity.User;
import com.practice.mapper.UserMapper;
import com.practice.mapper.UserReadMapper;
import com.practice.repository.UserRepository;
import com.practice.util.QPredicates;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.practice.domain.entity.QUser.user;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserMapper userMapper;

    public PageResponse<UserReadDto> findAll(UserFilterDto filterDto, Pageable pageable) {
        Predicate predicate = QPredicates.builder()
                .add(filterDto.username(), user.username::containsIgnoreCase)
                .add(filterDto.role(), user.role::eq)
                .build();

        Page<User> pageResponse = userRepository.findAll(predicate, pageable);

        List<UserReadDto> users = pageResponse.getContent().stream()
                .map(userReadMapper::map)
                .collect(toList());

        return new PageResponse<>(users, Pagination.of(pageResponse));
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto dto) {
        return Optional.of(dto)
                .map(userMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto dto) {
        return userRepository.findById(id)
                .map(user -> userMapper.map(dto, user))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
}
