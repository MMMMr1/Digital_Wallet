package com.michalenok.wallet.model.dto.response;

import com.michalenok.wallet.model.enums.OperationStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OperationInfoDto ( UUID uuid,
                                 UUID accountTo,
                                 String referenceNumber,
                                 BigDecimal amount,
                                 String currencyCode,
                                 OperationStatus status,
                                 Instant createdAt,
                                 Instant updatedAt){

}