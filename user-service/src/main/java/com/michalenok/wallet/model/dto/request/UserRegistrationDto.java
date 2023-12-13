package com.michalenok.wallet.model.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.michalenok.wallet.validator.ValidatedMail;
import com.michalenok.wallet.validator.ValidatedPassword;
import com.michalenok.wallet.validator.ValidatedPhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserRegistrationDto (
                                   @Schema(defaultValue = "VladMichs@gmail.com", description = "Email address")
                                   @ValidatedMail
                                   String mail,
                                   @Schema(defaultValue = "331112233", description = "User cell phone number")
                                   @ValidatedPhoneNumber
                                   String mobilePhone,
                                   @Schema(defaultValue = "12345678", description = "Password")
                                   @ValidatedPassword
                                   String password){
}
