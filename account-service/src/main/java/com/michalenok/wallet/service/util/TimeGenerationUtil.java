package com.michalenok.wallet.service.util;

import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class TimeGenerationUtil {

    public LocalDate generateCurrentLocalDate() {
        return LocalDate.now();
    }
}