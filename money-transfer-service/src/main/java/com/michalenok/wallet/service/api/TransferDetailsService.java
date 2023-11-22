package com.michalenok.wallet.service.api;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.model.enums.TransferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface TransferDetailsService {

    Page<TransferInfoDto> getTransfers(Pageable pageable);
    Page<TransferInfoDto> searchTransfers(TransferType transferType,
                                          TransferStatus transferStatus,
                                          Instant timeAfter,
                                          Instant timeBefore,
                                          Pageable pageable);

    TransferInfoDto getTransfer(UUID uuid);

    List<TransferInfoDto> getTransfers(UUID accountUuid);

    List<TransferInfoDto> getDebitTransfers(UUID accountUuid);

    List<TransferInfoDto> getCreditTransfers(UUID accountUuid);

    List<TransferInfoDto> getInternalTransfers(UUID accountUuid);

    List<TransferInfoDto> getTransfersByPeriod(UUID accountUuid, Instant timeAfter, Instant timeBefore);
}