package com.practice.rest;

import com.practice.domain.dto.*;
import com.practice.exception.AvatarNotFoundException;
import com.practice.exception.UserNotFoundException;
import com.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/current")
    public RestResponse<UserReadDto> findCurrent(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .map(RestResponse::of)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/{userId}")
    public RestResponse<UserReadDto> findById(@PathVariable long userId) {
        return userService.findById(userId)
                .map(RestResponse::of)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping(value = "/{userId}/avatar", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public byte[] findAvatarById(@PathVariable long userId) {
        return userService.findAvatarById(userId).orElseThrow(AvatarNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated UserCreateDto userCreateDto) {
        long userId = userService.create(userCreateDto);

        return ResponseEntity.created(URI.create("/users/" + userId)).build();
    }

    @PutMapping(value = "/{userId}/avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveAvatar(@PathVariable long userId, @RequestParam("file") MultipartFile file) {
        userService.saveAvatar(userId, file);
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
