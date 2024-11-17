package com.practice.rest;

import com.practice.domain.dto.*;
import com.practice.exception.UserNotFoundException;
import com.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public RestResponse<List<UserReadDto>> findAll(UserFilterDto filterDto, Pageable pageable) {
        return userService.findAll(filterDto, pageable);
    }

    @GetMapping("/{userId}")
    public RestResponse<UserReadDto> findById(@PathVariable long userId) {
        return userService.findById(userId)
                .map(RestResponse::of)
                .orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated UserCreateDto userCreateDto) {
        long userId = userService.create(userCreateDto);

        return ResponseEntity.created(URI.create("/users/" + userId)).build();
    }

    @PatchMapping("/{userId}")
    public void update(@PathVariable long userId, @Validated UserEditDto userEditDto) {
        userService.update(userId, userEditDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @DeleteMapping
    public void delete(long userId) {
        if (!userService.delete(userId)) {
            throw new UserNotFoundException();
        }
    }
}
