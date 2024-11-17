package com.practice.exception.handler;

import com.practice.domain.dto.RestResponse;
import com.practice.domain.dto.RestResponse.RestErrors;
import com.practice.exception.RestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestResponse<?>> handleRestException(RestException e) {
        RestResponse<?> response = new RestResponse<>();
        response.addError(RestErrors.of(e));

        return ResponseEntity
                .status(e.getStatus())
                .body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        RestResponse<?> response = new RestResponse<>();

        if (ex.getBindingResult().hasErrors()) {
            ex.getBindingResult().getFieldErrors().stream()
                    .map(error -> RestErrors.of(status.value(), error))
                    .forEach(response::addError);
        }

        return ResponseEntity.status(status.value()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
