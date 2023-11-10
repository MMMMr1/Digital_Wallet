package com.michalenok.wallet.model.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
                          Instant createdAt,
                          Instant updatedAt) {
}
