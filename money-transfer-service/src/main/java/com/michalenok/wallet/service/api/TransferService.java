package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;

public interface TransferService {
    void debitTransfer(TransferRequestDto debit);

    void creditTransfer(TransferRequestDto credit);

    void internalFundTransfer(TransferRequestDto transfer);
}