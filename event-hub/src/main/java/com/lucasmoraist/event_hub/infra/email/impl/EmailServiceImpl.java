package com.lucasmoraist.event_hub.infra.email.impl;

import com.lucasmoraist.event_hub.infra.email.EmailService;
import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.model.EmailData;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;

    @Override
    public void confirmEmail(EmailData data) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(data.to());
            helper.setSubject(String.format("Olá %s! Confirme seu e-mail.", data.name()));
            helper.setText(this.buildMessageToConfirm(data.name(), data.to()), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error creating email message", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void confirmInscription(Inscriptions inscriptions) {
        User user = this.getUserById(inscriptions.getUserId());
        Events event = this.getEventById(inscriptions.getEventId());

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(user.getEmail());
            helper.setSubject("Confirme sua presença no evento");
            helper.setText(this.buildMessageToInscription(user, event, inscriptions.getId()), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error creating email message for inscription confirmation", e);
            throw new RuntimeException(e);
        }
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    private Events getEventById(String eventId) {
        return this.eventsRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
    }

    private String buildMessageToConfirm(String name, String email) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Confirmação de E-mail</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f6f8;
                            padding: 20px;
                        }
                        .container {
                            background-color: #ffffff;
                            border-radius: 8px;
                            padding: 30px;
                            max-width: 600px;
                            margin: 0 auto;
                            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                        }
                        h2 {
                            color: #2e86de;
                        }
                        p {
                            font-size: 16px;
                            color: #333333;
                        }
                        .footer {
                            margin-top: 20px;
                            font-size: 14px;
                            color: #999999;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Confirmação de E-mail</h2>
                        <p>Olá <strong>%s</strong>,</p>

                        <p>Para confirmar seu e-mail, clique no link abaixo:</p>
                        <p><a href="%s/confirm?email=%s">Confirmar E-mail</a></p>

                        <p>Atenciosamente,<br>
                        Equipe de Suporte</p>

                        <div class="footer">
                            Este é um e-mail automático. Por favor, não responda.
                        </div>
                    </div>
                </html>
                """, name, this.url, email);

    }

    private String buildMessageToInscription(User user, Events events, String inscriptionId) {
        return String.format("""
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Confirmação de Presença</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f6f8;
                            padding: 20px;
                        }
                        .container {
                            background-color: #ffffff;
                            border-radius: 8px;
                            padding: 30px;
                            max-width: 600px;
                            margin: 0 auto;
                            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
                        }
                        h2 {
                            color: #2e86de;
                        }
                        p {
                            font-size: 16px;
                            color: #333333;
                        }
                        .footer {
                            margin-top: 20px;
                            font-size: 14px;
                            color: #999999;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>Confirmação de Presença</h2>
                        <p>Olá <strong>%s</strong>,</p>

                        <p>Confirme sua presença no evento <strong>%s</strong>, que acontecerá em:</p>
                        <p><strong>Data:</strong> %s<br>
                           <strong>Horário:</strong> %s<br>
                           <strong>Local:</strong> %s</p>

                        <p>Clique no link abaixo para confirmar sua presença</p>
                        <p><a href="%s/inscriptions/confirm/%s">Confirmar Presença</a></p>

                        <p>Atenciosamente,<br>
                        Equipe de Organização</p>

                        <div class="footer">
                            Este é um e-mail automático. Por favor, não responda.
                        </div>
                    </div>
                </body>
                </html>
                """, user.getName(), events.getTitle(), events.getDate(), events.getTime(), events.getLocation(), this.url, inscriptionId);
    }

}
