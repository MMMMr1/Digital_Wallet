package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;

public interface TransferValidationService {
    void validateDebitTransfer(TransferRequestDto debit);

    void validateCreditTransfer(TransferRequestDto credit);

    void validateInternalFundTransfer(TransferRequestDto transfer);
}