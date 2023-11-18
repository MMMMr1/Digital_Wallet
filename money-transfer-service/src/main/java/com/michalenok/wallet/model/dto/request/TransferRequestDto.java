package com.michalenok.wallet.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(@NotBlank
                                 @Schema(defaultValue = "ebf96539-bd68-4cc1-9811-9d8482f157b8", description = "Account uuid")
                                 UUID accountTo,
                                 @NotBlank
                                 @Schema(defaultValue = "ebf96539-bd68-4cc1-9811-9d8482f157b8", description = "Referebce number or uuid")
                                 String referenceNumber,
                                 @Positive(message = "Should be positive")
                                 @Schema(defaultValue = "100.00", description = "Amount")
                                 BigDecimal amount,
                                 @NotBlank
                                 @Schema(defaultValue = "EUR", description = "Currency code")
                                 String currencyCode){
}