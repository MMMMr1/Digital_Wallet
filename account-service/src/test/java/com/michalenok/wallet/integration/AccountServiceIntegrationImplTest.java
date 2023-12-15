package com.michalenok.wallet.integration;

import com.michalenok.wallet.kafka.listener.TransferMessageListenerImpl;
import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.exception.AccountNotFoundException;
import com.michalenok.wallet.service.api.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

 class AccountServiceIntegrationImplTest extends IntegrationTestBase {
    @Autowired
    private AccountService accountService;
    @MockBean
    private TransferMessageListenerImpl transferMessageListener;

    @Test
    void create_Successful() {
        UUID uuid = UUID.randomUUID();
        AccountInfoDto accountInfoDto = accountService.create(uuid);
        AccountInfoDto byAccountId = accountService.findByAccountId(accountInfoDto.accountNumber());
        assertEquals(accountInfoDto.accountNumber(), byAccountId.accountNumber());
        assertEquals(accountInfoDto.currentBalance(), byAccountId.currentBalance());
        assertEquals(accountInfoDto.currencyCode(), byAccountId.currencyCode());
        assertEquals(accountInfoDto.blockedSum(), byAccountId.blockedSum());
        assertEquals(accountInfoDto.maxLimit(), byAccountId.maxLimit());
        assertEquals(accountInfoDto.isActive(), byAccountId.isActive());
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
        List<AccountInfoDto> allByClientId = accountService.findAllByClientId(clientIdExists);
        AccountInfoDto firstAccountInfoDto = allByClientId.get(0);
        AccountInfoDto secondAccountInfoDto = allByClientId.get(1);
        assertEquals(2, allByClientId.size());
        assertEquals(UUID.fromString("767762e6-85df-48b0-bc60-ff0520416e16"), firstAccountInfoDto.accountNumber());
        assertEquals(UUID.fromString("56a1e73b-2e0c-42a4-805f-7aa2f377a43c"), secondAccountInfoDto.accountNumber());
    }

    @Test
    void updateCurrentBalance_Successful() {
        UUID accountId = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        assertNotEquals(new BigDecimal(100), accountService.findByAccountId(accountId).currentBalance());
        AccountInfoDto accountInfoDto = accountService.updateCurrentBalance(accountId, new BigDecimal(100));
        assertEquals(new BigDecimal(100), accountInfoDto.currentBalance());
    }

    @Test
    void close_Successful() {
        UUID accountId = UUID.fromString("ad16c450-386f-427b-a5e5-763577425e5d");
        assertNotEquals(false, accountService.findByAccountId(accountId).isActive());
        AccountInfoDto accountInfoDto = accountService.close(accountId);
        assertEquals(false, accountInfoDto.isActive());
    }
}