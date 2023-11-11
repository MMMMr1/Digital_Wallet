package com.michalenok.wallet.model.dto;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record AccountInfoDto(UUID accountNumber,
                             String currencyCode,
                             BigDecimal currentBalance,
                             LocalDate openDate,
                             LocalDate closeDate,
                             boolean isActive,
                             BigDecimal blockedSum){
}
