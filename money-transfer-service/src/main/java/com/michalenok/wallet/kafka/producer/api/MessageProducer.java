package com.michalenok.wallet.kafka.producer.api;

import java.util.UUID;

public interface MessageProducer<T> {
    void sendMessage(T message, UUID id, String topic);
}