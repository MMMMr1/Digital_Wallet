package com.michalenok.wallet.model.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.michalenok.wallet.validator.util.jackson.CustomInstantConverter;
import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserInfoDto(UUID uuid,
                          String mail,
                          String mobilePhone,
                          Set<UserRole> role,
                          UserStatus status,
                          @JsonSerialize(converter = CustomInstantConverter.Serializer.class)
                          Instant dtCreate,
                          @JsonSerialize(converter = CustomInstantConverter.Serializer.class)
                          Instant dtUpdate) {
}
