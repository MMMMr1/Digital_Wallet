package com.michalenok.wallet.model.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccountInfoDto(UUID accountNumber,
                             String currencyCode,
                             BigDecimal currentBalance,
                             Instant openDate,
                             Instant closeDate,
                             boolean isActive,
                             BigDecimal maxLimit,
                             BigDecimal blockedSum){
}
