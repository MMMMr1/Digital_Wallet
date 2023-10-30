package com.michalenok.wallet.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidatedPhoneNumber {
    String message() default "Invalid cell phone number. Example of the correct variant: 29233XXX";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}