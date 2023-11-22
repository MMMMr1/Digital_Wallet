package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;

public interface TransferService {
    TransferInfoDto debitTransfer(TransferRequestDto debit);

    TransferInfoDto creditTransfer(TransferRequestDto credit);

    TransferInfoDto internalFundTransfer(TransferRequestDto transfer);
}