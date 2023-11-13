package com.michalenok.wallet.service;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.model.entity.TransferEntity;
import com.michalenok.wallet.model.enums.TransferType;
import com.michalenok.wallet.repository.TransferRepository;
import com.michalenok.wallet.service.api.AccountService;
import com.michalenok.wallet.service.api.TransferService;
import com.michalenok.wallet.service.api.TransferValidationService;
import com.michalenok.wallet.service.util.TimeGenerationUtil;
import com.michalenok.wallet.service.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transactionRepository;
    private final AccountService accountService;
    private final TransferValidationService validationService;
    private final TimeGenerationUtil timeGenerationUtil;
    private final UuidUtil uuidUtil;

    @Override
    @Transactional
    public TransferInfoDto debitTransfer(TransferRequestDto debit) {
        validationService.isValidDebitTransfer(debit);
        AccountInfoDto account = accountService.findByAccountId(debit.accountTo());
        accountService.updateCurrentBalance(debit.accountTo(), account.currentBalance().add(debit.amount()));
        log.info("debit debit for account {}, amount {}", debit.accountTo(), debit.amount());
        return saveTransaction(debit, TransferType.DEBIT);
    }

    @Override
    @Transactional
    public TransferInfoDto creditTransfer(TransferRequestDto credit) {
        validationService.isValidCreditTransfer(credit);
        AccountInfoDto account = accountService.findByAccountId(credit.accountTo());
        accountService.updateCurrentBalance(credit.accountTo(), account.currentBalance().add(credit.amount().negate()));
        log.info("credit transfer for account {}, amount {}", credit.accountTo(), credit.amount());
        return saveTransaction(credit, TransferType.CREDIT);
    }

    @Override
    @Transactional
    public TransferInfoDto internalFundTransfer(TransferRequestDto transfer){
        validationService.isValidInternalFundTransfer(transfer);
        AccountInfoDto accountTo = accountService.findByAccountId(transfer.accountTo());
        AccountInfoDto accountFrom = accountService.findByAccountId(UUID.fromString(transfer.referenceNumber()));
        accountService.updateCurrentBalance(accountFrom.accountNumber(), accountFrom.currentBalance().add(transfer.amount().negate()));
        accountService.updateCurrentBalance(accountTo.accountNumber(), accountTo.currentBalance().add(transfer.amount()));
        log.info("internal fund transfer [{}]", transfer.toString());
        return saveTransaction(transfer, TransferType.INTERNAL_TRANSFER);
    }

    private TransferInfoDto saveTransaction(TransferRequestDto transfer, TransferType transactionType){
        TransferEntity transactionEntity = new TransferEntity();
        transactionEntity.setTransactionUuid(uuidUtil.generateUuid());
        transactionEntity.setCreatedAt(timeGenerationUtil.generateCurrentInstant());
        transactionEntity.setAccountEntity(accountService.getAccount(transfer.accountTo()));
        transactionEntity.setTransactionType(transactionType);
        transactionEntity.setReferenceNumber(transfer.referenceNumber());
        transactionEntity.setCurrencyCode(transfer.currencyCode());
        transactionEntity.setAmount(transfer.amount());
        transactionRepository.save(transactionEntity);
        log.info("save transaction with uuid [{}]", transactionEntity.toString());
        return TransferInfoDto.builder()
                .transactionId(transactionEntity.getTransactionUuid())
                .message("Transfer successfully completed")
                .build();
    }
}