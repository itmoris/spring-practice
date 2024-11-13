package com.practice.domain.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> data,
        Pagination pagination
) {
    public record Pagination(
            Integer page,
            Integer size,
            Integer total
    ) {
        public static Pagination of(Page<?> page) {
            return new Pagination(page.getNumber(), page.getSize(), page.getTotalPages());
        }
    }
}
