package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.service.api.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/api/v1/money-transfers")
@RequiredArgsConstructor
public class MoneyTransferController {
    private final TransferService transferService;

    @Operation(summary = "TS1: Account replenishment", tags = "transfers")
    @PostMapping("/debit-transfer")
    public ResponseEntity<?> debitTransfer(@RequestBody TransferRequestDto debitTransferRequest) {
        log.info("Debit transfer initiated from {}", debitTransferRequest.toString());
        transferService.debitTransfer(debitTransferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "TS2: Cash transfer from the account", tags = "transfers")
    @PostMapping("/credit-transfer")
    public ResponseEntity<?> creditPayment(@RequestBody TransferRequestDto creditTransferRequest) {
        log.info("Credit transfer initiated from {}", creditTransferRequest.toString());
        transferService.creditTransfer(creditTransferRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "TS3: Internal transfer of funds", tags = "transfers")
    @PostMapping("/internal-transfer")
    public ResponseEntity<?>  internalPayment(@RequestBody TransferRequestDto transferRequestDto) {
        log.info("Internal transfer initiated from {}", transferRequestDto.toString());
        transferService.internalFundTransfer(transferRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "All transfers of funds", tags = "transfers")
    @GetMapping
    protected Page<TransferInfoDto> getAll(Pageable pageable) {
        return transferService.getPage(pageable);
    }
}