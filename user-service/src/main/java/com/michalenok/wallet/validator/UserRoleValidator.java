package com.michalenok.wallet.validator;

import com.michalenok.wallet.model.constant.UserRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;


public class UserRoleValidator implements ConstraintValidator<UserRoleValid, Set<UserRole>> {
    private UserRole[] userRoles;
    @Override
    public void initialize(UserRoleValid constraintAnnotation) {
        this.userRoles = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Set<UserRole> value, ConstraintValidatorContext context) {
        return value.stream()
                .anyMatch(val -> Arrays.asList(userRoles).contains(val));
    }

//    @Override
//    public boolean isValid(UserRole value, ConstraintValidatorContext context) {
//
//
//        return value == null || Arrays.asList(userRoles).contains(value);
//    }
}
