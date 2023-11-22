package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.service.api.TransferDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/money-transfers/details")
@RequiredArgsConstructor
public class MoneyTransferDetailsController {
    private final TransferDetailsService transferDetailsService;

    @Operation(summary = "Show all funds transfers", tags = "transfer details")
    @GetMapping
    protected Page<TransferInfoDto> showTransfers(Pageable pageable) {
        log.info("Get all transfers of funds");
        return transferDetailsService.getTransfers(pageable);
    }

    @Operation(summary = "Show funds transfer details", tags = "transfer details")
    @GetMapping(path = "/{uuid}")
    protected TransferInfoDto showTransferDetails(@PathVariable("uuid") @NotBlank UUID uuid) {
        log.info("Transfer details for uuid {}", uuid);
        return transferDetailsService.getTransfer(uuid);
    }

    @Operation(summary = "Show all funds transfers for account", tags = "client transfer details")
    @GetMapping(path = "/account/{account_uuid}")
    protected List<TransferInfoDto> showTransfers(@PathVariable("account_uuid") @NotBlank UUID accountUuid) {
        log.info("Show all funds transfers for account with uuid {}", accountUuid);
        return transferDetailsService.getTransfers(accountUuid);
    }

    @Operation(summary = "Show all funds transfers for account by period", tags = "client transfer details")
    @GetMapping(path = "/account")
    protected List<TransferInfoDto> showTransfersByPeriod(@RequestParam UUID accountUuid,
                                                          @RequestParam @Past Instant timeAfter,
                                                          @RequestParam @Past Instant timeBefore ) {
        log.info("Show all funds transfers for account {} for some period {}, {}", accountUuid, timeAfter, timeBefore);
        return transferDetailsService.getTransfersByPeriod(accountUuid, timeAfter, timeBefore);
    }

    @Operation(summary = "Show all debit transfers of funds for account", tags = "client transfer details")
    @GetMapping(path = "/debit/{account_uuid}")
    protected List<TransferInfoDto> showDebitTransfers(@PathVariable("account_uuid") @NotBlank UUID accountUuid) {
        log.info("Show all debit transfers of funds for account with uuid {}", accountUuid);
        return transferDetailsService.getDebitTransfers(accountUuid);
    }

    @Operation(summary = "Show all credit funds transfers for account", tags = "client transfer details")
    @GetMapping(path = "/credit/{account_uuid}")
    protected List<TransferInfoDto> showCreditTransfers(@PathVariable("account_uuid") @NotBlank UUID accountUuid) {
        log.info("Show all credit funds transfers for account with uuid {}", accountUuid);
        return transferDetailsService.getCreditTransfers(accountUuid);
    }

    @Operation(summary = "Show all internal funds transfers for account", tags = "client transfer details")
    @GetMapping(path = "/internal/{account_uuid}")
    protected List<TransferInfoDto> showInternalTransfers(@PathVariable("account_uuid") @NotBlank UUID accountUuid) {
        log.info("Show all internal funds transfers for account with uuid {}", accountUuid);
        return transferDetailsService.getInternalTransfers(accountUuid);
    }
}