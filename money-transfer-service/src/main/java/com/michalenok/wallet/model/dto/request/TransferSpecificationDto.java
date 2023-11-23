package com.michalenok.wallet.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.enums.TransferStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

public record TransferSpecificationDto(
        @Positive(message = "Should be positive")
        @JsonProperty(required = false)
        @Schema(defaultValue = "100.00", description = "Min value of amount")
        BigDecimal amountMin,
        @Positive(message = "Should be positive")
        @JsonProperty(required = false)
        @Schema(defaultValue = "100.00", description = "Max value of amount")
        BigDecimal amountMax,
        @JsonProperty(required = false)
        @Schema(defaultValue = "CREDIT, DEBIT, INTERNAL", description = "Transfer type")
        TransferType transferType,
        @JsonProperty(required = false)
        @Schema(defaultValue = "CREATED, COMPLETED, FAILED", description = "Transfer status")
        TransferStatus transferStatus,
        @JsonProperty(required = false)
        @PastOrPresent
        Instant timeAfter,
        @JsonProperty(required = false)
        @PastOrPresent
        Instant timeBefore){
}