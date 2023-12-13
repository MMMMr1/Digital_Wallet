package com.michalenok.wallet.kafka.listener;

import com.michalenok.wallet.kafka.command.ExecutorTransferCommand;
import com.michalenok.wallet.kafka.listener.api.TransferListener;
import com.michalenok.wallet.kafka.producer.api.TransactionProducer;
import com.michalenok.wallet.kafka.schema.Transaction;
import com.michalenok.wallet.kafka.schema.TransactionStatus;
import com.michalenok.wallet.kafka.schema.Transfer;
import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.mapper.TransactionMapper;
import com.michalenok.wallet.mapper.TransferMapper;
import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.service.api.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topic.transfer}", groupId = "${spring.kafka.consumer.group-id}")
public class TransferMessageListenerImpl implements TransferListener<Transfer> {
    private final TransferService transferService;
    private final TransactionProducer<Transaction> transactionProducer;
    private final TransferMapper transferMapper;
    private final TransactionMapper transactionMapper;
    @Value("${kafka.topic.transaction}")
    private String topic;

    @KafkaHandler
    public void listenTransfer(Transfer message) {
        Transaction transaction = transactionMapper.toTransfer(message);
        log.info("new transfer {}", transaction.toString());

        try {
            TransferType transferType = TransferType.valueOf(message.getType());
            TransferRequestDto transferRequestDto = transferMapper.toTransfer(message);

            ExecutorTransferCommand executorTransferCommand = new ExecutorTransferCommand();
            executorTransferCommand.transfer(transferType)
                    .transfer(transferService,transferRequestDto);
            log.info("successful transfer of TransferRequestDto {}", transferRequestDto);

            transaction.setStatus(TransactionStatus.SUCCESSFUL.name());
            transactionProducer.sendMessage(transaction, topic);
        } catch (RuntimeException e) {
            log.error("unsuccessful transfer {}", e.getMessage());
            transaction.setStatus(TransactionStatus.UNSUCCESSFUL.name());
            transactionProducer.sendMessage(transaction, topic);
        }
    }
}