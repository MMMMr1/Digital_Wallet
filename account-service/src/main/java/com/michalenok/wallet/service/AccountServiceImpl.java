package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.AccountMapper;
import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.entity.AccountEntity;
import com.michalenok.wallet.model.exception.AccountNotFoundException;
import com.michalenok.wallet.repository.AccountRepository;
import com.michalenok.wallet.service.api.AccountService;
import com.michalenok.wallet.service.util.TimeGenerationUtil;
import com.michalenok.wallet.service.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TimeGenerationUtil timeGenerationUtil;
    private final UuidUtil uuidUtil;

    @Override
    @Transactional
    public AccountInfoDto create(UUID user_uuid) {
        return accountMapper.toAccountInfo(
                accountRepository.save(initializeNewAccount(user_uuid)));
    }

    @Override
    @Transactional
    public AccountInfoDto updateCurrentBalance(UUID accountUuid, BigDecimal newBalance) {
        return accountRepository.findById(accountUuid)
                .map(account -> {
                    account.setCurrentBalance(newBalance);
                    return account;
                })
                .map(accountMapper::toAccountInfo)
                .orElseThrow(() ->
                        new AccountNotFoundException(String.format("Account with uuid {%s} not exist", accountUuid)));
    }

    @Override
    @Transactional
    public AccountInfoDto close(UUID accountUuid) {
        return accountRepository.findById(accountUuid)
                .map(account -> {
                    account.setIsActive(Boolean.FALSE);
                    account.setCloseDate(timeGenerationUtil.generateCurrentInstant());
                    accountRepository.save(account);
                    return account;
                })
                .map(accountMapper::toAccountInfo)
                .orElseThrow(() ->
                        new AccountNotFoundException(String.format("Account with uuid {%s} not exist", accountUuid)));
    }

    @Override
    public Page<AccountInfoDto> getPage(Pageable paging) {
        return accountRepository.findAll(paging)
                .map(accountMapper::toAccountInfo);
    }

    @Override
    public AccountInfoDto findByAccountId(UUID uuid) {
        return accountRepository.findById(uuid).map(accountMapper::toAccountInfo).orElseThrow(() ->
                new AccountNotFoundException(String.format("Account with uuid {%s} not found", uuid)));
    }

    @Override
    public List<AccountInfoDto> findAllByClientId(UUID uuid) {
        return accountRepository.findByClientId(uuid).stream()
                .map(accountMapper::toAccountInfo)
                .toList();
    }

    @Override
    public AccountEntity getAccount(UUID uuid) {
        return accountRepository.findById(uuid)
                .orElseThrow(() ->
                        new AccountNotFoundException(String.format("Account with uuid {%s} not found", uuid)));
    }

    private AccountEntity initializeNewAccount(UUID userUuid) {
        AccountEntity account = new AccountEntity();
        account.setAccountNumber(uuidUtil.generateUuid());
        account.setClientId(userUuid);
        account.setCurrencyCode("EUR");
        account.setCurrentBalance(BigDecimal.ZERO);
        account.setBlockedSum(BigDecimal.ZERO);
        account.setOpenDate(timeGenerationUtil.generateCurrentInstant());
        account.setIsActive(true);
        account.setMaxLimit(BigDecimal.valueOf(100000));
        log.info("initialize account {} for user with uuid {}, open date {}",
                account.getAccountNumber(), account.getClientId(), account.getOpenDate());
        return account;
    }
}