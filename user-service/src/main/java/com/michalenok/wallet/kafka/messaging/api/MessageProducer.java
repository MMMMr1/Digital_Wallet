package com.michalenok.wallet.kafka.messaging.api;

public interface MessageProducer<T> {
    void sendMessage( T message);
}