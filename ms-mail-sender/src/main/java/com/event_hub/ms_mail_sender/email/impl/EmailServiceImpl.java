package com.event_hub.ms_mail_sender.email.impl;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.email.EmailService;
import com.event_hub.ms_mail_sender.exceptions.EmailException;
import com.event_hub.ms_mail_sender.utils.EmailMessageUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${app.environment.url}")
    private String url;
    private final JavaMailSender mailSender;

    @Override
    public void confirmEmail(ConfirmEmailData data) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(data.to());
            helper.setSubject("Confirme seu e-mail!");
            helper.setText(EmailMessageUtils.buildMessageToConfirm(data, url), true);
            log.debug("Sending confirmation email to: {}", data.to());

            mailSender.send(message);
            log.info("Confirmation email sent successfully to: {}", data.to());
        } catch (MessagingException e) {
            log.error("Error creating email message", e);
            throw new EmailException("Error creating email message", e);
        }
    }

    @Override
    public void confirmInscription(ConfirmInscriptionData data) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(data.userEmail());
            helper.setSubject("Confirme sua presença no evento!");
            helper.setText(EmailMessageUtils.buildMessageToInscription(data, url), true);
            log.debug("Sending inscription confirmation email to: {}", data.userEmail());

            mailSender.send(message);
            log.info("Inscription confirmation email sent successfully to: {}", data.userEmail());
        } catch (MessagingException e) {
            log.error("Error creating email message for inscription confirmation", e);
            throw new EmailException("Error creating email message for inscription confirmation", e);
        }
    }

}
