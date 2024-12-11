package com.practice.controller;

import com.practice.service.SimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SimpleController {
    private final SimpleService simpleService;

    @GetMapping("/users/{id}")
    public String findById(@PathVariable Integer id) {
        return simpleService.findById(id);
    }
}
