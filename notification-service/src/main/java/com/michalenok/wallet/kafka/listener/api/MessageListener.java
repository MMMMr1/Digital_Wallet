package com.michalenok.wallet.kafka.listener.api;

import jakarta.mail.MessagingException;

public interface MessageListener <T> {
    void listenMessage (T message) throws MessagingException;
}