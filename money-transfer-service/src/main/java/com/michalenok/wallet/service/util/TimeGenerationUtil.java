package com.michalenok.wallet.service.util;

import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
public class TimeGenerationUtil {

    public Instant generateCurrentInstant() {
        return Instant.now();
    }
}