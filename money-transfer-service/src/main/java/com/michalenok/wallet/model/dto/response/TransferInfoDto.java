package com.michalenok.wallet.model.dto.response;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.enums.TransferStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferInfoDto(UUID uuid,
                              UUID accountTo,
                              String referenceNumber,
                              BigDecimal amount,
                              String currencyCode,
                              TransferStatus status,
                              TransferType type,
                              Instant createdAt,
                              Instant updatedAt){
}