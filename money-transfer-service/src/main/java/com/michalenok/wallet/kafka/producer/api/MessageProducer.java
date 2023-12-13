package com.michalenok.wallet.kafka.producer.api;

public interface MessageProducer<T> {
    void sendMessage(T transfer);
}