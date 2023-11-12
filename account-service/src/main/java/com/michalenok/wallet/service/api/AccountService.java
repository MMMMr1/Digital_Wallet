package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.AccountInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountInfoDto create(UUID userUuid);

    AccountInfoDto close(UUID accountUuid);

    Page<AccountInfoDto> getPage(Pageable paging);

    AccountInfoDto findByAccountId(UUID uuid);

    List<AccountInfoDto> findAllByClientId(UUID uuid);
}
