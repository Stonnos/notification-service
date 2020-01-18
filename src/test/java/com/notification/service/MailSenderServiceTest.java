package com.notification.service;

import com.notification.TestHelperUtils;
import com.notification.model.EmailStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for checking {@link MailSenderService} functionality.
 *
 * @author Roman Batygin
 */
@RunWith(MockitoJUnitRunner.class)
public class MailSenderServiceTest {

    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private MailSenderService mailSenderService;

    @Test(expected = IllegalArgumentException.class)
    public void testNullEmail() throws MessagingException {
        mailSenderService.sendEmail(null);
    }

    @Test
    public void testSuccessSent() throws MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        mailSenderService.sendEmail(TestHelperUtils.createEmail(LocalDateTime.now(), EmailStatus.NEW));
        verify(mailSender, atLeastOnce()).send(mimeMessage);
    }
}