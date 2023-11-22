package com.michalenok.wallet.service;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.exception.AccountNotActiveException;
import com.michalenok.wallet.model.exception.ExceedingBalanceLimitException;
import com.michalenok.wallet.model.exception.InsufficientFundsException;
import com.michalenok.wallet.model.exception.CurrencyCodeMismatchException;
import com.michalenok.wallet.service.api.AccountService;
import com.michalenok.wallet.service.api.TransferValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferValidationServiceImpl implements TransferValidationService {
    private final AccountService accountService;

    @Override
    public void validateDebitTransfer(TransferRequestDto debit) {
        AccountInfoDto account = accountService.findByAccountId(debit.accountTo());
        validateIfActive(account);
        validateCurrencyCode(account, debit.currencyCode());
        validateDebitBalance(account, debit.amount());
    }

    @Override
    public void validateCreditTransfer(TransferRequestDto credit) {
        AccountInfoDto account = accountService.findByAccountId(credit.accountTo());
        validateIfActive(account);
        validateCurrencyCode(account, credit.currencyCode());
        validateCreditBalance(account, credit.amount());
    }

    @Override
    public void validateInternalFundTransfer(TransferRequestDto transfer) {
        AccountInfoDto accountTo = accountService.findByAccountId(transfer.accountTo());
        AccountInfoDto accountFrom = accountService.findByAccountId(UUID.fromString(transfer.referenceNumber()));

        validateIfActive(accountTo);
        validateIfActive(accountFrom);

        validateCurrencyCode(accountTo, accountFrom.currencyCode());
        validateCurrencyCode(accountTo, transfer.currencyCode());
        validateCurrencyCode(accountFrom, transfer.currencyCode());

        validateCreditBalance(accountFrom, transfer.amount());
        validateDebitBalance(accountTo, transfer.amount());
    }

    private void validateDebitBalance(AccountInfoDto account, BigDecimal amount) {
        BigDecimal total = account.currentBalance().add(amount);
        if (account.maxLimit().compareTo(total) < 0) {
            throw new ExceedingBalanceLimitException(String.format("Account with uuid {%s} can not to exceed the limit", account.accountNumber()));
        }
    }

    private void validateCreditBalance(AccountInfoDto account, BigDecimal amount) {
        if ((account.currentBalance().add(account.blockedSum().negate())).compareTo(amount) < 1) {
            throw new InsufficientFundsException(String.format("Insufficient funds in account {%s}", account.accountNumber()));
        }
    }

    private void validateIfActive(AccountInfoDto account) {
        if (!account.isActive()) {
            throw new AccountNotActiveException(String.format("Account with uuid {%s} not active", account.accountNumber()));
        }
    }

    private void validateCurrencyCode(AccountInfoDto account, String currencyCode) {
        if (!Objects.equals(account.currencyCode(), currencyCode)) {
            throw new CurrencyCodeMismatchException(String.format("Account with uuid {%s} has another currency code", account.accountNumber()));
        }
    }
}