package com.michalenok.wallet.service.util;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.UUID;

@Component
public class CreationUserUtil {
    public UUID generateUserUUID() {
        return UUID.randomUUID();
    }

    public Instant generateTimeCreatedAt() {
        return Instant.now();
    }

    public Instant generateTimeUpdateAt() {
        return Instant.now();
    }
}