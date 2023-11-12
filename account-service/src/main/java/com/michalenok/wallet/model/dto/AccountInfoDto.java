package com.michalenok.wallet.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccountInfoDto(UUID accountNumber,
                             String currencyCode,
                             BigDecimal currentBalance,
                             Instant openDate,
                             Instant closeDate,
                             boolean isActive,
                             BigDecimal blockedSum){
}
