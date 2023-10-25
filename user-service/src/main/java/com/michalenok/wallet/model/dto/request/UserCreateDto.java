package com.michalenok.wallet.model.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import com.michalenok.wallet.validator.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

import static com.michalenok.wallet.validator.util.ValidationConstant.REGEXP_VALIDATE_EMAIL;
import static com.michalenok.wallet.validator.util.ValidationConstant.REGEXP_VALIDATE_PHONE_NUMBER;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserCreateDto (
                             @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
                             @Pattern(regexp = REGEXP_VALIDATE_EMAIL,
                                     message = "Invalid email. Example of the correct variant: example@example.com ")
                             @Schema(defaultValue = "VladMichs@gmail.com", description = "Email address")
                             String mail,
                             @Length(max = 25, message = "The user's phone number is too long, the max number of symbols is 25")
                             @Pattern(regexp = REGEXP_VALIDATE_PHONE_NUMBER,
                                     message = "Invalid cell phone number. Example of the correct variant: 29233XXXX")
                             @Schema(defaultValue = "331112233", description = "User cell phone number")
                             String mobilePhone,
                             @UserRoleValid(anyOf = {UserRole.ADMIN, UserRole.USER})
                             @Schema(defaultValue = "[\"ADMIN\" , \"USER\"]", description = "User roles")
                             Set<UserRole> role,
                             @UserStatusValid(anyOf = {UserStatus.ACTIVATED,UserStatus.DEACTIVATED,UserStatus.WAITING_ACTIVATION})
                             @Schema(defaultValue = "ACTIVATED, DEACTIVATED, WAITING_ACTIVATION", description = "User status")
                             UserStatus status,
                             @PasswordValid
                             @Length(max = 30, message = "The user's phone number is too long, the max number of symbols is 30")
                             @Schema(defaultValue = "12345678", description = "Password")
                             String password){
}

