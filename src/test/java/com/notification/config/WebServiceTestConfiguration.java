package com.notification.config;

import com.notification.controller.EmailEndpoint;
import com.notification.dto.EmailRequest;
import com.notification.service.EmailService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Web service test configuration.
 *
 * @author Roman Batygin
 */
@TestConfiguration
public class WebServiceTestConfiguration {

    @MockBean
    private EmailService emailService;

    /**
     * Creates jaxb2 marshaller bean.
     *
     * @return jaxb2 marshaller bean
     */
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath(EmailRequest.class.getPackage().getName());
        return jaxb2Marshaller;
    }

    /**
     * Creates email endpoint bean.
     *
     * @return email endpoint bean
     */
    @Bean
    public EmailEndpoint emailEndpoint() {
        return new EmailEndpoint(emailService);
    }
}