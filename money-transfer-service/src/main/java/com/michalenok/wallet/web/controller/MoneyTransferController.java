package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.service.api.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/money-transfers")
@RequiredArgsConstructor
public class MoneyTransferController {
    private final TransferService transferService;

    @Operation(summary = "Account replenishment", tags = "transfers")
    @PostMapping("/debit-transfer")
    public TransferInfoDto debitTransfer(@RequestBody TransferRequestDto debitTransferRequest) {
        log.info("Debit transfer initiated from {}", debitTransferRequest.toString());
        return transferService.debitTransfer(debitTransferRequest);
    }

    @Operation(summary = "Cash transfer from the account", tags = "transfers")
    @PostMapping("/credit-transfer")
    public TransferInfoDto creditPayment(@RequestBody TransferRequestDto creditTransferRequest) {
        log.info("Credit transfer initiated from {}", creditTransferRequest.toString());
        return transferService.creditTransfer(creditTransferRequest);
    }

    @Operation(summary = "Internal transfer of funds", tags = "transfers")
    @PostMapping("/internal-transfer")
    public TransferInfoDto internalPayment(@RequestBody @Validated TransferRequestDto transferRequestDto) {
        log.info("Internal transfer initiated from {}", transferRequestDto.toString());
        return transferService.internalFundTransfer(transferRequestDto);
    }
}