package com.michalenok.wallet.model.dto.request;

import com.michalenok.wallet.validator.PasswordValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import static com.michalenok.wallet.validator.util.ValidationConstant.REGEXP_VALIDATE_EMAIL;

public record UserLoginDto (
                            @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
                            @Pattern(regexp = REGEXP_VALIDATE_EMAIL,
                                     message = "Invalid email. Example of the correct variant: example@example.com ")
                            @Schema(defaultValue = "VladMichs@gmail.com", description = "Email address")
                             String mail,
                            @PasswordValid
                            @Length(max = 30, message = "The user's phone number is too long, the max number of symbols is 30")
                            @Schema(defaultValue = "12345678", description = "Password")
                            String password){
}
