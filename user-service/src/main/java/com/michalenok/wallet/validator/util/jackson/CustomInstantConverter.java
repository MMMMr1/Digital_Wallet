package com.michalenok.wallet.validator.util.jackson;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Instant;

public class CustomInstantConverter {
    public static class Serializer extends StdConverter<Instant, Long> {
        @Override
        public Long convert(Instant value) {
            return value == null ? null : value.toEpochMilli();
        }
    }
}
