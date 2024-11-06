package com.practice.controller;

import com.practice.domain.dto.UserCreateEditDto;
import com.practice.domain.dto.UserReadDto;
import com.practice.domain.enums.Role;
import com.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @PostMapping
    public String create(UserCreateEditDto dto) {
        UserReadDto user = userService.create(dto);
        return "redirect:/users/%s".formatted(user.id());
    }

    //    @PutMapping not available in html form
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, UserCreateEditDto dto) {
        return userService.update(id, dto)
                .map(user -> "redirect:/users/%s".formatted(user.id()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //    @DeleteMapping("/{id}") not available in html form
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/users";
    }
}
