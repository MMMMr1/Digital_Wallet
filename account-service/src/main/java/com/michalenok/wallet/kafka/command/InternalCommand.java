package com.michalenok.wallet.kafka.command;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.service.api.TransferService;

public class InternalCommand implements TransferCommand {

    @Override
    public void transfer(TransferService transferService, TransferRequestDto transferRequestDto) {
        transferService.debitTransfer(transferRequestDto);
    }
}