package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.service.api.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/transfers")
public class TransferController {

    private final TransferService transferService;

    @Operation(summary = "TS1: Account replenishment", tags = "transfers")
    @PostMapping("/debit-transfer")
    public void debitTransfer(@RequestBody TransferRequestDto debitTransferRequest) {
        log.info("Debit transfer initiated from {}", debitTransferRequest.toString());
        transferService.debitTransfer(debitTransferRequest);
    }

    @Operation(summary = "TS2: Cash transfer from the account", tags = "transfers")
    @PostMapping("/credit-transfer")
    public void creditPayment(@RequestBody TransferRequestDto creditTransferRequest) {
        log.info("Credit transfer initiated from {}", creditTransferRequest.toString());
        transferService.creditTransfer(creditTransferRequest);
    }

    @Operation(summary = "TS3: Internal transfer of funds", tags = "transfers")
    @PostMapping("/internal-transfer")
    public void internalPayment(@RequestBody TransferRequestDto transferRequestDto) {
        log.info("Internal transfer initiated from {}", transferRequestDto.toString());
        transferService.internalFundTransfer(transferRequestDto);
    }
}