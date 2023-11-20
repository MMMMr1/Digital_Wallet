package com.michalenok.wallet.feign;

import com.michalenok.wallet.model.exception.AccountServiceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Log4j2
@Component
public class AccountServiceFallback implements FallbackFactory<AccountServiceFeignClient>{

    @Override
    public AccountServiceFeignClient create(Throwable cause) {
        return new AccountServiceFeignClient() {
            @Override
            public ResponseEntity<?> createAccount(UUID uuid) {
                log.info("fallback; reason was: {}, {}", cause.getMessage(), cause);
                throw new AccountServiceNotFoundException("Currently account service is down. We are working to resolve the issue");
            }
        };
    }
}