package com.michalenok.wallet.service.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidUtil {
    public UUID generateUuid() {
        return UUID.randomUUID();
    }
}