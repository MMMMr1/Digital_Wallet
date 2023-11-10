package com.michalenok.wallet.service.util;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class VerificationUserUtil {
    public String generateCode() {
        return UUID.randomUUID().toString();
    }
}