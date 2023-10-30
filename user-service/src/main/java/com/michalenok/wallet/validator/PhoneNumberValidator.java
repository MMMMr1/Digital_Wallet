package com.michalenok.wallet.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import static com.michalenok.wallet.validator.util.ValidationConstant.REGEXP_VALIDATE_PHONE_NUMBER;

public class PhoneNumberValidator implements ConstraintValidator<ValidatedPhoneNumber, String> {
    @Override
    public void initialize(final ValidatedPhoneNumber arg0) {
    }

    @Override
    public boolean isValid(final String mail, final ConstraintValidatorContext context) {
        return mail.isBlank() || mail.length() <=  25 || mail.matches(REGEXP_VALIDATE_PHONE_NUMBER);
    }
}
