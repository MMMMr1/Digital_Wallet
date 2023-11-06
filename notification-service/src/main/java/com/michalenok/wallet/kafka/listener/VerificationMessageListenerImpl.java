package com.michalenok.wallet.kafka.listener;

import com.michalenok.wallet.kafka.listener.api.MessageListener;
import com.michalenok.wallet.kafka.schema.Verification;
import com.michalenok.wallet.service.api.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${kafka.topic.verification}", groupId = "${spring.kafka.consumer.group-id}")
public class VerificationMessageListenerImpl implements MessageListener<Verification> {
    private final MailService mailService;

    @KafkaHandler
    public void listenMessage(Verification message) throws MessagingException {
        mailService.sendVerificationMessage(message);
        log.info("Send verification message  ["+ message +"]");
    }
}
