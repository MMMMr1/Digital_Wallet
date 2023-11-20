package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransferService {
    void debitTransfer(TransferRequestDto debit);

    void creditTransfer(TransferRequestDto credit);

    void internalFundTransfer(TransferRequestDto transfer);
    Page<TransferInfoDto> getPage(Pageable pageable);
}