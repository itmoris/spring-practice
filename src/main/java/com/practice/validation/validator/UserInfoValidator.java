package com.practice.validation.validator;

import com.practice.domain.dto.UserCreateEditDto;
import com.practice.validation.custom.UserInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.springframework.util.StringUtils.hasText;

// is bean
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        return hasText(value.firstname()) || hasText(value.lastname());
    }
}
