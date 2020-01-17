package com.notification.controller;

import com.notification.TestHelperUtils;
import com.notification.config.WebServiceTestConfiguration;
import com.notification.model.Email;
import com.notification.repository.EmailRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

/**
 * Tests for checking {@link EmailEndpoint} functionality.
 *
 * @author Roman Batygin
 */
@RunWith(SpringRunner.class)
@AutoConfigureDataJpa
@EnableJpaRepositories(basePackageClasses = EmailRepository.class)
@EntityScan(basePackageClasses = Email.class)
@EnableConfigurationProperties
@TestPropertySource("classpath:application.properties")
@Import(WebServiceTestConfiguration.class)
public class EmailEndpointTest {

    @Inject
    private ApplicationContext applicationContext;
    @Inject
    private Jaxb2Marshaller jaxb2Marshaller;
    @Inject
    private EmailRepository emailRepository;

    private Resource xsdSchema = new ClassPathResource("notification.xsd");

    private MockWebServiceClient mockClient;

    @Before
    public void init() {
        deleteAll();
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @After
    public void after() {
        deleteAll();
    }

    @Test
    public void testEmailSaving() throws IOException {
        StringSource request = getPayload(TestHelperUtils.createEmailRequest());
        mockClient.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(validPayload(xsdSchema));
        List<Email> emails = emailRepository.findAll();
        Assertions.assertThat(emails).hasSize(1);
    }

    private StringSource getPayload(Object object) {
        StringResult stringResult = new StringResult();
        jaxb2Marshaller.marshal(object, stringResult);
        return new StringSource(stringResult.toString());
    }

    private void deleteAll() {
        emailRepository.deleteAll();
    }
}
