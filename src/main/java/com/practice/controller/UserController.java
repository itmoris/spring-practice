package com.practice.controller;

import com.practice.domain.dto.UserCreateEditDto;
import com.practice.domain.dto.UserFilterDto;
import com.practice.domain.dto.UserReadDto;
import com.practice.domain.enums.Role;
import com.practice.service.UserService;
import com.practice.validation.group.CreateAction;
import com.practice.validation.group.UpdateAction;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error(e.getMessage(), e);
        return "error/error500";
    }

    @GetMapping
    public String findAll(Model model, UserFilterDto filterDto, Pageable pageable) {
        model.addAttribute("filters", filterDto);
        model.addAttribute("pageResponse", userService.findAll(filterDto, pageable));
        model.addAttribute("roles", Role.values());
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
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto dto) {
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Validated({Default.class, CreateAction.class}) UserCreateEditDto dto, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("user", dto);
            attributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        UserReadDto user = userService.create(dto);
        return "redirect:/users/%s".formatted(user.id());
    }

    //    @PutMapping not available in html form
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @Validated({Default.class, UpdateAction.class}) UserCreateEditDto dto) {
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
