package com.michalenok.wallet.service.api;

import com.michalenok.wallet.kafka.schema.Verification;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendVerificationMessage(Verification message) throws MessagingException;
}