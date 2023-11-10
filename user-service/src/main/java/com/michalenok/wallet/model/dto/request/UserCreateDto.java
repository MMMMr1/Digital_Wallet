package com.michalenok.wallet.model.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.validator.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.util.Set;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserCreateDto (
                             @Schema(defaultValue = "VladMichs@gmail.com", description = "Email address")
                             @ValidatedMail
                             String mail,
                             @Schema(defaultValue = "331112233", description = "User cell phone number")
                             @ValidatedPhoneNumber
                             String mobilePhone,
                             @Schema(defaultValue = "[\"ADMIN\" , \"USER\"]", description = "User roles")
                             Set<@ValidatedEnum(enumClass = UserRole.class) String> role,
                             @Schema(defaultValue = "ACTIVATED, DEACTIVATED, WAITING_ACTIVATION", description = "User status")
                             @ValidatedEnum(enumClass = UserStatus.class)
                             String status,
                             @Schema(defaultValue = "12345678", description = "Password")
                             @ValidatedPassword
                             String password){
}

