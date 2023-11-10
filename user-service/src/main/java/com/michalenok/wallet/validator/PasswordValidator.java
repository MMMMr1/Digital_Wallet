package com.michalenok.wallet.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.LengthRule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.passay.PasswordData;
import java.util.Arrays;

public class PasswordValidator implements ConstraintValidator<ValidatedPassword, String> {
    @Override
    public void initialize(final ValidatedPassword arg0) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        final org.passay.PasswordValidator validator = new org.passay.PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new WhitespaceRule()));
        final RuleResult result = validator.validate(new PasswordData(password));
        return (result.isValid());
    }
}
