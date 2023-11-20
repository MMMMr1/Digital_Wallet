package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountInfoDto create(UUID userUuid);

    AccountInfoDto close(UUID accountUuid);

    Page<AccountInfoDto> getAccounts(Pageable paging);

    AccountInfoDto findByAccountId(UUID uuid);

    List<AccountInfoDto> findAllByClientId(UUID uuid);

    AccountInfoDto updateCurrentBalance(UUID accountUuid, BigDecimal newBalance);

    AccountEntity getAccount(UUID uuid);
}
