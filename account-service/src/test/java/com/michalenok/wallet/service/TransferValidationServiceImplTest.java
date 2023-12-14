package com.michalenok.wallet.service;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.exception.*;
import com.michalenok.wallet.service.api.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferValidationServiceImplTest {
    @InjectMocks
    private TransferValidationServiceImpl validationService;
    @Mock
    private AccountService accountService;

    @Test
    void validateDebitTransfer_AccountNotFoundException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenThrow(AccountNotFoundException.class);
        assertThrows(AccountNotFoundException.class, () -> validationService.validateDebitTransfer(transferRequestDto));
    }

    @Test
    void validateDebitTransfer_AccountNotActiveException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(false)
                        .build());
        assertThrows(AccountNotActiveException.class, () ->
                validationService.validateDebitTransfer(transferRequestDto));
    }

    @Test
    void validateDebitTransfer_CurrencyCodeMismatchException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "USD");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(true)
                        .currencyCode("EUR")
                        .build());
        assertThrows(CurrencyCodeMismatchException.class, () ->
                validationService.validateDebitTransfer(transferRequestDto));
    }

    @Test
    void validateDebitTransfer_ExceedingBalanceLimitException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100000000),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(true)
                        .currencyCode("EUR")
                        .maxLimit(new BigDecimal(100000))
                        .currentBalance(new BigDecimal(10000))
                        .build());
        assertThrows(ExceedingBalanceLimitException.class, () ->
                validationService.validateDebitTransfer(transferRequestDto));
    }

    @Test
    void validateCreditTransfer_AccountNotFoundException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenThrow(AccountNotFoundException.class);
        assertThrows(AccountNotFoundException.class, () -> validationService.validateCreditTransfer(transferRequestDto));
    }

    @Test
    void validateCreditTransfer_AccountNotActiveException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(false)
                        .build());
        assertThrows(AccountNotActiveException.class, () ->
                validationService.validateCreditTransfer(transferRequestDto));
    }

    @Test
    void validateCreditTransfer_CurrencyCodeMismatchException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "USD");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(true)
                        .currencyCode("EUR")
                        .build());
        assertThrows(CurrencyCodeMismatchException.class, () ->
                validationService.validateCreditTransfer(transferRequestDto));
    }

    @Test
    void validateCreditTransfer_ExceedingBalanceLimitException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(true)
                        .currencyCode("EUR")
                        .maxLimit(new BigDecimal(100000))
                        .currentBalance(new BigDecimal(10))
                        .blockedSum(new BigDecimal(10))
                        .build());
        assertThrows(InsufficientFundsException.class, () ->
                validationService.validateCreditTransfer(transferRequestDto));
    }

    @Test
    void validateInternalFundTransfer_AccountNotFoundException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenThrow(AccountNotFoundException.class);
        assertThrows(AccountNotFoundException.class,
                () -> validationService.validateInternalFundTransfer(transferRequestDto));
    }

    @Test
    void validateInternalFundTransfer_AccountNotActiveException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(false)
                        .build());
        assertThrows(AccountNotActiveException.class, () ->
                validationService.validateInternalFundTransfer(transferRequestDto));
    }

    @Test
    void validateInternalFundTransfer_CurrencyCodeMismatchException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "USD");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(true)
                        .currencyCode("EUR")
                        .build());
        assertThrows(CurrencyCodeMismatchException.class, () ->
                validationService.validateInternalFundTransfer(transferRequestDto));
    }

    @Test
    void validateInternalFundTransfer_ExceedingBalanceLimitException() {
        TransferRequestDto transferRequestDto = getTransferRequestDto(new BigDecimal(100),
                "EUR");
        when(accountService.findByAccountId(any(UUID.class)))
                .thenReturn(AccountInfoDto.builder()
                        .isActive(true)
                        .currencyCode("EUR")
                        .maxLimit(new BigDecimal(100000))
                        .currentBalance(new BigDecimal(10))
                        .blockedSum(new BigDecimal(10))
                        .build());
        assertThrows(InsufficientFundsException.class, () ->
                validationService.validateInternalFundTransfer(transferRequestDto));
    }
    private TransferRequestDto getTransferRequestDto(BigDecimal amount, String currency) {
        return new TransferRequestDto(UUID.randomUUID(),
                UUID.randomUUID().toString(),
                amount,
                currency);
    }

}