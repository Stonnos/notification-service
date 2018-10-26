package com.notification.mapping;

import com.notification.dto.EmailRequest;
import com.notification.model.Email;
import com.notification.model.EmailStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.notification.TestHelperUtils.createEmailRequest;

/**
 * Unit tests for checking {@link EmailRequestMapper} functionality.
 */
@RunWith(SpringRunner.class)
@Import(EmailRequestMapperImpl.class)
public class EmailRequestMapperTest {

    @Inject
    private EmailRequestMapper emailRequestMapper;

    @Test
    public void testMapEmailRequest() {
        EmailRequest emailRequest = createEmailRequest();
        Email email = emailRequestMapper.map(emailRequest);
        Assertions.assertThat(email.getSender()).isEqualTo(emailRequest.getSender());
        Assertions.assertThat(email.getReceiver()).isEqualTo(emailRequest.getReceiver());
        Assertions.assertThat(email.getSubject()).isEqualTo(emailRequest.getSubject());
        Assertions.assertThat(email.getMessage()).isEqualTo(emailRequest.getMessage());
        Assertions.assertThat(email.isHtml()).isEqualTo(emailRequest.isHtml());
        Assertions.assertThat(email.getStatus()).isEqualTo(EmailStatus.NEW);
    }
}
