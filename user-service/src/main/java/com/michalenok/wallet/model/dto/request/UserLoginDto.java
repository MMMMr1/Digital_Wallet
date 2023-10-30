package com.michalenok.wallet.model.dto.request;

import com.michalenok.wallet.validator.ValidatedMail;
import com.michalenok.wallet.validator.ValidatedPassword;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginDto (
                            @Schema(defaultValue = "VladMichs@gmail.com", description = "Email address")
                            @ValidatedMail
                            String mail,
                            @Schema(defaultValue = "12345678", description = "Password")
                            @ValidatedPassword
                            String password){
}
