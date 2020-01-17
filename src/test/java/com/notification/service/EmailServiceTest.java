package com.notification.service;

import com.notification.AbstractJpaTest;
import com.notification.TestHelperUtils;
import com.notification.dto.EmailRequest;
import com.notification.dto.EmailResponse;
import com.notification.dto.ResponseStatus;
import com.notification.mapping.EmailRequestMapper;
import com.notification.mapping.EmailRequestMapperImpl;
import com.notification.model.Email;
import com.notification.repository.EmailRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.util.ReflectionTestUtils;

import javax.inject.Inject;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for checking {@link EmailService} functionality.
 *
 * @author Roman Batygin
 */
@Import(EmailRequestMapperImpl.class)
public class EmailServiceTest extends AbstractJpaTest {

    @Inject
    private EmailRepository emailRepository;
    @Inject
    private EmailRequestMapper emailRequestMapper;

    private EmailService emailService;

    @Override
    public void init() {
        emailService = new EmailService(emailRepository, emailRequestMapper);
    }

    @Override
    public void deleteAll() {
        emailRepository.deleteAll();
    }

    @Test
    public void testInvalidEmailRequest() {
        EmailRequest emailRequest = TestHelperUtils.createEmailRequest();
        emailRequest.setReceiver("dw@dw..ru");
        EmailResponse emailResponse = emailService.saveEmail(emailRequest);
        assertResponse(emailResponse, ResponseStatus.INVALID_REQUEST_PARAMS);
    }

    @Test
    public void testErrorStatus() {
        EmailRepository mockRepository = Mockito.mock(EmailRepository.class);
        ReflectionTestUtils.setField(emailService, "emailRepository", mockRepository);
        when(mockRepository.save(any(Email.class))).thenThrow(new InvalidDataAccessApiUsageException("Error"));
        EmailRequest emailRequest = TestHelperUtils.createEmailRequest();
        EmailResponse emailResponse = emailService.saveEmail(emailRequest);
        assertResponse(emailResponse, ResponseStatus.ERROR);
    }

    @Test
    public void testEmailSaving() {
        EmailRequest emailRequest = TestHelperUtils.createEmailRequest();
        EmailResponse emailResponse = emailService.saveEmail(emailRequest);
        assertResponse(emailResponse, ResponseStatus.SUCCESS);
        List<Email> emails = emailRepository.findAll();
        Assertions.assertThat(emails).hasSize(1);
    }

    private void assertResponse(EmailResponse emailResponse, ResponseStatus expected) {
        Assertions.assertThat(emailResponse).isNotNull();
        Assertions.assertThat(emailResponse.getRequestId()).isNotNull();
        Assertions.assertThat(emailResponse.getStatus()).isEqualTo(expected);
    }
}
