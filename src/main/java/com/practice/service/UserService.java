package com.practice.service;

import com.practice.domain.dto.*;
import com.practice.domain.dto.RestResponse.Pagination;
import com.practice.domain.entity.User;
import com.practice.exception.UserAlreadyExistsException;
import com.practice.exception.UserNotFoundException;
import com.practice.mapper.UserCreateToUserMapper;
import com.practice.mapper.UserEditToUserMapper;
import com.practice.mapper.UserToUserReadMapper;
import com.practice.repository.UserRepository;
import com.practice.util.QPredicates;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.practice.domain.entity.QUser.user;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final ImageService imageService;
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

    public Optional<UserReadDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userToUserReadMapper::map);
    }

    public Optional<UserReadDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userToUserReadMapper::map);
    }

    public Optional<byte[]> findAvatarById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return Optional.of(user)
                .filter(u -> nonNull(u.getImagePath()) && !u.getImagePath().isBlank())
                .map(User::getImagePath)
                .map(imagePath -> Path.of(imagePath).getFileName().toString())
                .map(imageService::getImage);
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

    @SneakyThrows
    @Transactional
    public void saveAvatar(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        String pathToImage = user.getImagePath();

        if (isNull(pathToImage) || pathToImage.isBlank()) {
            pathToImage = imageService.saveImage(randomUUID().toString(), file.getInputStream());
        } else {
            Path filename = Path.of(pathToImage).getFileName();
            pathToImage = imageService.saveImage(filename.toString(), file.getInputStream());
        }

        user.setImagePath(pathToImage);
        userRepository.flush();
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
