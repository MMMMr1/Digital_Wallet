package com.michalenok.wallet.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccountInfoDto(UUID accountNumber,
                             String currencyCode,
                             BigDecimal currentBalance,
                             LocalDate openDate,
                             LocalDate closeDate,
                             boolean isActive,
                             BigDecimal blockedSum){
}
