package com.michalenok.wallet.kafka.producer;

import com.michalenok.wallet.kafka.producer.api.TransactionProducer;
import com.michalenok.wallet.kafka.schema.Transaction;
import com.michalenok.wallet.mapper.TransferMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class TransactionMessageProducerImpl implements TransactionProducer<Transaction> {
    private final KafkaTemplate<String, Transaction> transferKafkaTemplate;

    @Override
    public void sendMessage(Transaction message, String operationTopicName) {
        CompletableFuture<SendResult<String, Transaction>> future =
                transferKafkaTemplate.send(operationTopicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Send message to topic [" + operationTopicName +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            else {
                log.error("Unable to send message to topic [" + operationTopicName +
                        "]  due to : " + ex.getMessage());
            }
        });
    }
}