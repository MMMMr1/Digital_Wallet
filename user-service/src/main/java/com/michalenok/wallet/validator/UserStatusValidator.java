package com.michalenok.wallet.validator;

import com.michalenok.wallet.model.constant.UserStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;


public class UserStatusValidator implements ConstraintValidator<UserStatusValid, UserStatus> {
    private UserStatus[] userStatuses;
    @Override
    public void initialize(UserStatusValid constraintAnnotation) {
        this.userStatuses = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(UserStatus value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(userStatuses).contains(value);
    }

//    @Override
//    public boolean isValid(UserRole value, ConstraintValidatorContext context) {
//
//
//        return value == null || Arrays.asList(userRoles).contains(value);
//    }
}
