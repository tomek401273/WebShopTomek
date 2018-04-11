package com.tgrajkowski.service.mail;

import com.tgrajkowski.model.mail.Mail;
import com.tgrajkowski.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
@RunWith(MockitoJUnitRunner.class)
public class SimpleMailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
//        // Given
//        Mail mail = new Mail("test@test.com","Test", "Test Message");
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(mail.getMailTo());
//        mailMessage.setSubject(mail.getSubject());
//        mailMessage.setText(mail.getMessage());
//        // When
//        simpleEmailService.send(mail);
//        verify(javaMailSender, times(1)).send(mailMessage);
    }
}