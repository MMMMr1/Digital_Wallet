package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.TransferSpecificationDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.service.api.TransferDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/money-transfers/details")
@RequiredArgsConstructor
public class MoneyTransferDetailsController {
    private final TransferDetailsService transferDetailsService;

    @Operation(summary = "Show funds transfer details", tags = "transfers details")
    @GetMapping(path = "/{uuid}")
    protected TransferInfoDto showTransferDetails(@PathVariable("uuid") @NotBlank UUID uuid) {
        log.info("Transfer details for uuid {}", uuid);
        return transferDetailsService.getTransfer(uuid);
    }

    @Operation(summary = "Show all funds transfers for account", tags = "transfers details")
    @GetMapping(path = "/account/{account_uuid}")
    protected List<TransferInfoDto> showTransfersByAccount(@PathVariable("account_uuid") @NotBlank UUID accountUuid) {
        log.info("Show all funds transfers for account with uuid {}", accountUuid);
        return transferDetailsService.getTransfers(accountUuid);
    }

    @Operation(summary = "Show all funds transfers", tags = "transfers details")
    @GetMapping
    protected Page<TransferInfoDto> showTransfers(Pageable pageable) {
        log.info("Get all transfers of funds");
        return transferDetailsService.getTransfers(pageable);
    }

    @Operation(summary = "Search funds transfers", tags = "transfers details")
    @PostMapping
    public Page<TransferInfoDto> searchTransfers(@RequestBody @Valid TransferSpecificationDto specificationDto,
                                                 Pageable pageable) {
        log.info("Search transfers of funds {}", specificationDto);
        return transferDetailsService.searchTransfers(specificationDto, pageable);
    }

    @Operation(summary = "Search funds transfers for account", tags = "transfers details")
    @PostMapping(path = "/account/{account_uuid}")
    protected Page<TransferInfoDto> searchTransfersByAccount(@PathVariable("account_uuid") @NotBlank UUID accountUuid,
                                                           @RequestBody @Valid TransferSpecificationDto specificationDto,
                                                           Pageable pageable) {
        log.info("Search funds transfers for account with uuid {}", accountUuid);
        return transferDetailsService.searchTransfersByAccount(accountUuid, specificationDto, pageable);
    }
}