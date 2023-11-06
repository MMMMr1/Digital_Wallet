package com.michalenok.wallet.service;

import com.michalenok.wallet.kafka.schema.Verification;
import com.michalenok.wallet.model.MessageTopic;
import com.michalenok.wallet.service.api.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import static com.michalenok.wallet.model.MessageTopic.VERIFICATION;

@Log4j2
@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${mail.username}")
    private String NOREPLY_ADDRESS = "maksim.maks.23@mail.ru";

    @Override
    public void sendVerificationMessage(Verification messageDto) throws MessagingException{
            MimeMessage message = createMessage(messageDto, VERIFICATION);
            mailSender.send(message);
            log.info("Sent notification to email: {}; with subject: {}.", messageDto.getMail(), VERIFICATION.name());
    }

    private MimeMessage createMessage(Verification messageDto, MessageTopic topic) throws MessagingException {
        Context context = new Context();
        Map<String, Object> forVerificationContext = new HashMap<>();
        forVerificationContext.put("code", messageDto.getCode());
        forVerificationContext.put("mail", messageDto.getMail());
        context.setVariables(forVerificationContext);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(NOREPLY_ADDRESS);
        mimeMessageHelper.setFrom(NOREPLY_ADDRESS);
        mimeMessageHelper.setSubject(topic.name());
        mimeMessageHelper.setText((templateEngine.process(topic.getPage(), context)), true);
        return message;
    }
}