package com.michalenok.wallet.kafka.listener;

import com.michalenok.wallet.kafka.listener.api.TransferListener;
import com.michalenok.wallet.kafka.producer.api.TransactionProducer;
import com.michalenok.wallet.kafka.schema.Transaction;
import com.michalenok.wallet.kafka.schema.TransactionStatus;
import com.michalenok.wallet.kafka.schema.Transfer;
import com.michalenok.wallet.mapper.TransactionMapper;
import com.michalenok.wallet.mapper.TransferMapper;
import com.michalenok.wallet.service.api.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topic.debit_operation}", groupId = "${spring.kafka.consumer.group-id}")
public class OperationMessageListenerImpl implements TransferListener<Transfer> {
    private final TransferService transferService;
    private final TransactionProducer<Transaction> transactionProducer;
    private final TransferMapper transferMapper;
    private final TransactionMapper transactionMapper;

    @KafkaHandler
    public void listenTransfer(Transfer message) {
        Transaction transaction = transactionMapper.toTransfer(message);
        try {
            transferService.debitTransfer(transferMapper.toTransfer(message));
            transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL.name());
            transactionProducer.sendMessage(transaction, "transaction_topic");
        } catch (RuntimeException e) {
            transaction.setTransactionStatus(TransactionStatus.UNSUCCESSFUL.name());
            transactionProducer.sendMessage(transaction, "transaction_topic");
        }
    }
}