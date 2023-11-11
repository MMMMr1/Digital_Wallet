package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.AccountMapper;
import com.michalenok.wallet.model.dto.AccountInfoDto;
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

    private AccountEntity initializeNewAccount(UUID user_uuid) {
        AccountEntity account = new AccountEntity();
        account.setAccountNumber(uuidUtil.generateUuid());
        account.setClientId(user_uuid);
        account.setCurrencyCode("EU");
        account.setCurrentBalance(BigDecimal.ZERO);
        account.setOpenDate(timeGenerationUtil.generateCurrentLocalDate());
        account.setIsActive(true);
        account.setMaxLimit(BigDecimal.valueOf(100000));
        log.info("initialize account {} for user with uuid {}, open date {}",
                account.getAccountNumber(), account.getClientId(), account.getOpenDate());
        return account;
    }
}