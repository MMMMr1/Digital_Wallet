package com.michalenok.wallet.kafka.listener.api;

public interface MessageListener <T> {
    void listenMessage (T message);
}