package com.michalenok.wallet.kafka.listener.api;


public interface TransferListener<T> {
    void listenTransfer(T transfer);
}