package com.practice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.practice.exception.RestException;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestResponse<T> {
    private T data;
    private Pagination pagination;
    private final List<RestErrors> errors = new ArrayList<>();

    public RestResponse(T data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public RestResponse() {
    }

    public static <T> RestResponse<T> of(T data) {
        return new RestResponse<>(data, null);
    }

    public void addError(RestErrors error) {
        this.errors.add(error);
    }

    public record RestErrors(
            Integer status,
            String field,
            String detail
    ) {
        public static RestErrors of(RestException e) {
            return new RestErrors(e.getStatus(), null, e.getMessage());
        }

        public static RestErrors of(Integer status, FieldError error) {
            return new RestErrors(status, error.getField(), error.getDefaultMessage());
        }
    }

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
