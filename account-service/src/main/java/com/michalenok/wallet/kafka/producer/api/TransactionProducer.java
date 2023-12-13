package com.michalenok.wallet.kafka.producer.api;

public interface TransactionProducer<T> {
    void sendMessage(T message, String topic);
}