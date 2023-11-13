package com.michalenok.wallet.model.dto.response;

import lombok.Builder;
import java.util.UUID;

@Builder
public record TransferInfoDto(UUID transactionId,
                              String message){
}