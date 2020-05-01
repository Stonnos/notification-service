package com.notification.controller;

import com.notification.TestHelperUtils;
import com.notification.config.WebServiceTestConfiguration;
import com.notification.dto.EmailRequest;
import com.notification.dto.EmailResponse;
import com.notification.service.EmailService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import javax.inject.Inject;
import java.io.IOException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.clientOrSenderFault;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

/**
 * Tests for checking {@link EmailEndpoint} functionality.
 *
 * @author Roman Batygin
 */
@TestPropertySource("classpath:application.properties")
@ExtendWith(SpringExtension.class)
@Import({WebServiceTestConfiguration.class, EmailEndpoint.class})
public class EmailEndpointTest {

    private static final int LARGE_SUBJECT_LENGTH = 256;

    @Inject
    private ApplicationContext applicationContext;
    @Inject
    private Jaxb2Marshaller jaxb2Marshaller;
    @MockBean
    private EmailService emailService;

    private Resource xsdSchema = new ClassPathResource("notification.xsd");

    private MockWebServiceClient mockClient;

    @BeforeEach
    public void init() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void testEmailSaving() throws IOException {
        EmailRequest emailRequest = TestHelperUtils.createEmailRequest();
        StringSource request = getPayload(emailRequest);
        EmailResponse emailResponse = TestHelperUtils.createEmailResponse(UUID.randomUUID().toString());
        StringSource response = getPayload(emailResponse);
        when(emailService.saveEmail(any(EmailRequest.class))).thenReturn(emailResponse);
        mockClient.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(payload(response))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void testInvalidEmailSaving() throws IOException {
        EmailRequest emailRequest = TestHelperUtils.createEmailRequest();
        emailRequest.setReceiver("email");
        StringSource request = getPayload(emailRequest);
        mockClient.sendRequest(withPayload(request)).andExpect(clientOrSenderFault());
    }

    @Test
    public void testEmptyMessageSaving() throws IOException {
        EmailRequest emailRequest = TestHelperUtils.createEmailRequest();
        emailRequest.setMessage(StringUtils.EMPTY);
        StringSource request = getPayload(emailRequest);
        mockClient.sendRequest(withPayload(request)).andExpect(clientOrSenderFault());
    }

    @Test
    public void testLargeSubjectSaving() throws IOException {
        EmailRequest emailRequest = TestHelperUtils.createEmailRequest();
        emailRequest.setSubject(StringUtils.repeat('Q', LARGE_SUBJECT_LENGTH));
        StringSource request = getPayload(emailRequest);
        mockClient.sendRequest(withPayload(request)).andExpect(clientOrSenderFault());
    }

    private StringSource getPayload(Object object) {
        StringResult stringResult = new StringResult();
        jaxb2Marshaller.marshal(object, stringResult);
        return new StringSource(stringResult.toString());
    }
}
