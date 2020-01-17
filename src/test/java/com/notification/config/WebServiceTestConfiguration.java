package com.notification.config;

import com.notification.controller.EmailEndpoint;
import com.notification.dto.EmailRequest;
import com.notification.mapping.EmailRequestMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * Web service test configuration.
 *
 * @author Roman Batygin
 */
@Configuration
@Import({EmailRequestMapperImpl.class, EmailEndpoint.class})
public class WebServiceTestConfiguration {

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
}