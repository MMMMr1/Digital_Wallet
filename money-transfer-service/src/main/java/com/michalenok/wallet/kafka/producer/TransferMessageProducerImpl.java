package com.michalenok.wallet.kafka.producer;

import com.michalenok.wallet.kafka.producer.api.MessageProducer;
import com.michalenok.wallet.kafka.schema.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
@RequiredArgsConstructor
public class TransferMessageProducerImpl implements MessageProducer<Transfer> {
    private final KafkaTemplate<String, Transfer> operationKafkaTemplate;
    @Value("${avro.topic.name.transfer}")
    private String transferTopicName;

    @Override
    public void sendMessage(Transfer transfer) {
        CompletableFuture<SendResult<String, Transfer>> future =
                operationKafkaTemplate.send(transferTopicName,transfer);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Send message to topic [" + transferTopicName +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            else {
                log.error("Unable to send message to topic [" + transferTopicName +
                        "]  due to : " + ex.getMessage());
            }
        });
    }
}

