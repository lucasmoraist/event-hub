package com.event_hub.ms_mail_sender.email.impl;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.email.EmailService;
import com.event_hub.ms_mail_sender.utils.EmailMessageUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void confirmEmail(ConfirmEmailData data) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(data.to());
            helper.setSubject(String.format("Olá %s! Confirme seu e-mail.", data.name()));
            helper.setText(EmailMessageUtils.buildMessageToConfirm(data), true);
            log.debug("Sending confirmation email to: {}", data.to());

            mailSender.send(message);
            log.info("Confirmation email sent successfully to: {}", data.to());
        } catch (MessagingException e) {
            // TODO: Implementar exceção personalizada
            log.error("Error creating email message", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void confirmInscription(ConfirmInscriptionData data) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(data.userEmail());
            helper.setSubject("Confirme sua presença no evento!");
            helper.setText(EmailMessageUtils.buildMessageToInscription(data), true);
            log.debug("Sending inscription confirmation email to: {}", data.userEmail());

            mailSender.send(message);
            log.info("Inscription confirmation email sent successfully to: {}", data.userEmail());
        } catch (MessagingException e) {
            // TODO: Implementar exceção personalizada
            log.error("Error creating email message for inscription confirmation", e);
            throw new RuntimeException(e);
        }
    }

}
