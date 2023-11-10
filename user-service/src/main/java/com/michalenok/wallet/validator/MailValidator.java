package com.michalenok.wallet.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import static com.michalenok.wallet.validator.util.ValidationConstant.REGEXP_VALIDATE_EMAIL;

public class MailValidator implements ConstraintValidator<ValidatedMail, String> {
    @Override
    public void initialize(final ValidatedMail arg0) {
    }

    @Override
    public boolean isValid(final String mail, final ConstraintValidatorContext context) {
        return mail.isBlank() || mail.length() <= 50 || mail.matches(REGEXP_VALIDATE_EMAIL);
    }
}
