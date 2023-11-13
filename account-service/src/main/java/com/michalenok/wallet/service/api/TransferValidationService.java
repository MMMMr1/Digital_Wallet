package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;

public interface TransferValidationService {
    void isValidDebitTransfer(TransferRequestDto debit);

    void isValidCreditTransfer(TransferRequestDto credit);

    void isValidInternalFundTransfer(TransferRequestDto transfer);
}