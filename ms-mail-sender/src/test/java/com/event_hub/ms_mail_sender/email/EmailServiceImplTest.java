package com.event_hub.ms_mail_sender.email;

import com.event_hub.ms_mail_sender.dto.ConfirmEmailData;
import com.event_hub.ms_mail_sender.dto.ConfirmInscriptionData;
import com.event_hub.ms_mail_sender.email.impl.EmailServiceImpl;
import com.event_hub.ms_mail_sender.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    JavaMailSender mailSender;
    @InjectMocks
    EmailServiceImpl emailService;
    @Mock
    MimeMessage mimeMessage;
    @Mock
    MimeMessageHelper mimeMessageHelper;

    @Test
    @DisplayName("Test confirmEmail method with valid data")
    void case01() {
        ConfirmEmailData emailData = new ConfirmEmailData("test@example.com", "Test User");

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.confirmEmail(emailData);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    @DisplayName("Test confirmEmail method with invalid data")
    void case02() throws MessagingException {
        ConfirmEmailData emailData = new ConfirmEmailData("test@example.com", "Test User");

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(MessagingException.class).when(mimeMessage).setContent(anyString(), anyString());

        assertThrows(RuntimeException.class, () -> emailService.confirmEmail(emailData));
    }

    @Test
    @DisplayName("Test confirmInscription method with valid data")
    void case03() {
        ConfirmInscriptionData inscriptions = new ConfirmInscriptionData(
                "1234567890",
                "Test User",
                "user@example.com",
                "Test Event",
                "2023-10-01",
                "10:00",
                "Test Location"
        );

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.confirmInscription(inscriptions);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    @DisplayName("Test confirmEmail method with MessagingException")
    void case04() {
        ConfirmEmailData emailData = new ConfirmEmailData("test@example.com", "Test User");

        when(mailSender.createMimeMessage()).thenThrow(new EmailException("Creation failed", new RuntimeException()));

        EmailException exception = assertThrows(EmailException.class, () -> emailService.confirmEmail(emailData));

        assertEquals("Creation failed", exception.getMessage());
    }

    @Test
    @DisplayName("Test confirmInscription method with MessagingException")
    void case05() {
        ConfirmInscriptionData inscriptions = new ConfirmInscriptionData(
                "1234567890",
                "Test User",
                "user@example.com",
                "Test Event",
                "2023-10-01",
                "10:00",
                "Test Location"
        );

        when(mailSender.createMimeMessage()).thenThrow(new EmailException("Creation failed", new RuntimeException()));

        EmailException exception = assertThrows(EmailException.class, () -> emailService.confirmInscription(inscriptions));

        assertEquals("Creation failed", exception.getMessage());
    }

}