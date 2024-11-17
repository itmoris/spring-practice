package com.practice.service;

import com.practice.domain.dto.*;
import com.practice.domain.dto.RestResponse.Pagination;
import com.practice.domain.entity.User;
import com.practice.exception.UserAlreadyExistsException;
import com.practice.mapper.UserCreateToUserMapper;
import com.practice.mapper.UserEditToUserMapper;
import com.practice.mapper.UserToUserReadMapper;
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
import java.util.stream.Collectors;

import static com.practice.domain.entity.QUser.user;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserToUserReadMapper userToUserReadMapper;
    private final UserCreateToUserMapper userCreateToUserMapper;
    private final UserEditToUserMapper userEditToUserMapper;

    public RestResponse<List<UserReadDto>> findAll(UserFilterDto filterDto, Pageable pageable) {
        Predicate predicates = QPredicates.builder()
                .add(user.email::contains, filterDto.email())
                .add(user.username::contains, filterDto.username())
                .add(user.firstname::contains, filterDto.firstname())
                .add(user.lastname::contains, filterDto.lastname())
                .add(user.role::eq, filterDto.role())
                .build();

        Page<User> page = userRepository.findAll(predicates, pageable);

        List<UserReadDto> users = page.getContent().stream()
                .map(userToUserReadMapper::map).toList();

        Pagination pagination = Pagination.of(page);

        return new RestResponse<>(users, pagination);
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userToUserReadMapper::map);
    }

    @Transactional
    public long create(UserCreateDto userCreateDto) {
        Optional<User> user = userRepository.findByUsernameOrEmail(userCreateDto.username(), userCreateDto.email());

        if (user.isEmpty()) {
            return Optional.of(userCreateDto)
                    .map(userCreateToUserMapper::map)
                    .map(userRepository::save)
                    .map(User::getId)
                    .orElseThrow(() -> new RuntimeException("User creation failed"));
        }

        throw new UserAlreadyExistsException();
    }

    @Transactional
    public Optional<UserReadDto> update(Long userId, UserEditDto userEditDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    User createdUser = userEditToUserMapper.map(userEditDto, user);
                    userRepository.flush();
                    return createdUser;
                })
                .map(userToUserReadMapper::map);
    }

    @Transactional
    public boolean delete(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }
}
