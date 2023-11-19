package com.michalenok.wallet.kafka.listener.api;

import java.util.UUID;

public interface TransferListener<T> {
    void listenTransfer(T transfer);
}