package com.michalenok.wallet.validator.util.jackson;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
@Component
public class StringToInstantConverter implements Converter<String,Instant> {
    @Override
    public Instant convert(String value) {
        Long decode =Long.decode(value);
        return Instant.ofEpochMilli(decode);
    }
}
