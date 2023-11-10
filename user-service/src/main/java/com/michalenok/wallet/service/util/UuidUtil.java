package com.michalenok.wallet.service.util;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class UuidUtil {
    public String generateUuidCode() {
        return UUID.randomUUID().toString();
    }
    public UUID generateUuid() {
        return UUID.randomUUID();
    }
}