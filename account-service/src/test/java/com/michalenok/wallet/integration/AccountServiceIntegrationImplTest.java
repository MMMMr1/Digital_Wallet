package com.michalenok.wallet.integration;

import com.michalenok.wallet.kafka.listener.TransferMessageListenerImpl;
import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.exception.AccountNotFoundException;
import com.michalenok.wallet.service.api.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Profile("test")
class AccountServiceIntegrationImplTest extends IntegrationTestBase {
    @Autowired
    private AccountService accountService;
    @MockBean
    private TransferMessageListenerImpl transferMessageListener;

    @Test
    void create_Successful() {
        UUID uuid = UUID.randomUUID();
        AccountInfoDto accountInfoDto = accountService.create(uuid);
        assertNotNull(accountInfoDto);
    }

    @Test
    void findAccountById_Successful() {
        UUID accountId = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        AccountInfoDto byAccountId = accountService.findByAccountId(accountId);
        assertEquals(accountId, byAccountId.accountNumber());
    }

    @Test
    void findAccountById_AccountNotFoundException() {
        UUID uuid = UUID.randomUUID();
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(uuid));
    }

    @Test
    void getAccounts_Successful() {
        assertEquals(3, accountService.getAccounts(Pageable.unpaged()).getTotalElements());
    }

    @Test
    void findAllByClientId_Successful() {
        UUID clientIdExists = UUID.fromString("3d4aa376-cf36-4421-a48b-f47320684da9");
        assertEquals(2, accountService.findAllByClientId(clientIdExists).size());
    }

    @Test
    void updateCurrentBalance_Successful() {
        UUID accountId = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        AccountInfoDto accountInfoDto = accountService.updateCurrentBalance(accountId, new BigDecimal(100));
        assertEquals(new BigDecimal(100), accountInfoDto.currentBalance());
    }

    @Test
    void close_Successful() {
        UUID accountId = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        AccountInfoDto accountInfoDto = accountService.close(accountId);
        assertEquals(false, accountInfoDto.isActive());
    }
}