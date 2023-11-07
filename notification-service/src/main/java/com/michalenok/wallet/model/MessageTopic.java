package com.michalenok.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum MessageTopic {
    VERIFICATION("verification");

    private final String page;
}