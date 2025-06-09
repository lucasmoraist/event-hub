package com.lucasmoraist.event_hub.infra.email;

import com.lucasmoraist.event_hub.domain.entity.Events;
import com.lucasmoraist.event_hub.domain.entity.Inscriptions;
import com.lucasmoraist.event_hub.domain.entity.User;
import com.lucasmoraist.event_hub.domain.model.EmailData;
import com.lucasmoraist.event_hub.infra.email.impl.EmailServiceImpl;
import com.lucasmoraist.event_hub.infra.repository.EventsRepository;
import com.lucasmoraist.event_hub.infra.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    JavaMailSender mailSender;
    @Mock
    UserRepository userRepository;
    @Mock
    EventsRepository eventsRepository;
    @InjectMocks
    EmailServiceImpl emailService;
    @Mock
    MimeMessage mimeMessage;
    @Mock
    MimeMessageHelper mimeMessageHelper;

    @Test
    @DisplayName("Test confirmEmail method with valid data")
    void case01() {
        EmailData emailData = new EmailData("test@example.com", "Test User");

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.confirmEmail(emailData);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    @DisplayName("Test confirmEmail method with invalid data")
    void case02() throws MessagingException {
        EmailData emailData = new EmailData("test@example.com", "Test User");

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(MessagingException.class).when(mimeMessage).setContent(anyString(), anyString());

        assertThrows(RuntimeException.class, () -> emailService.confirmEmail(emailData));
    }

    @Test
    @DisplayName("Test confirmInscription method with valid data")
    void case03() {
        Inscriptions inscriptions = new Inscriptions();
        inscriptions.setUserId("user123");
        inscriptions.setEventId("event123");
        inscriptions.setId("inscription123");

        User user = new User();
        user.setId("user123");
        user.setEmail("user@example.com");
        user.setName("Test User");

        Events event = new Events();
        event.setId("event123");
        event.setTitle("Test Event");
        event.setDate(LocalDate.of(2023, 10, 1));
        event.setTime(LocalTime.of(10, 0));
        event.setLocation("Test Location");

        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(eventsRepository.findById("event123")).thenReturn(Optional.of(event));
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.confirmInscription(inscriptions);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    @DisplayName("Test confirmInscription method with non-existent user")
    void case04() {
        Inscriptions inscriptions = new Inscriptions();
        inscriptions.setUserId("user123");
        inscriptions.setEventId("event123");

        when(userRepository.findById("user123")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> emailService.confirmInscription(inscriptions));
    }

}