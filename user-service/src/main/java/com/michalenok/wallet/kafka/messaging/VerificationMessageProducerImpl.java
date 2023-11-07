package com.michalenok.wallet.kafka.messaging;

import com.michalenok.wallet.kafka.messaging.api.MessageProducer;
import com.michalenok.wallet.kafka.schema.Verification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
@RequiredArgsConstructor
public class VerificationMessageProducerImpl implements MessageProducer<Verification> {

    private final KafkaTemplate<String, Verification> verificationKafkaTemplate;
    @Value("${avro.topic.name.verification}")
    private String verificationTopicName;

    @Override
    public void sendMessage(Verification message) {
        CompletableFuture<SendResult<String, Verification>> future = verificationKafkaTemplate.send(verificationTopicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Send message to topic [" + verificationTopicName +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            else {
                log.error("Unable to send message to topic [" + verificationTopicName +
                        "]  due to : " + ex.getMessage());
            }
        });
    }
}
