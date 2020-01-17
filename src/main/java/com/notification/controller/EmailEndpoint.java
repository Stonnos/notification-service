package com.notification.controller;

import com.notification.dto.EmailRequest;
import com.notification.dto.EmailResponse;
import com.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Email endpoint.
 */
@Slf4j
@Endpoint
@RequiredArgsConstructor
public class EmailEndpoint {

    private static final String NAMESPACE_URI = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String EMAIL_REQUEST_LOCAL_PART = "emailRequest";

    private final EmailService emailService;

    /**
     * Saves email request.
     *
     * @param emailRequest - email request
     * @return email response
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = EMAIL_REQUEST_LOCAL_PART)
    @ResponsePayload
    public EmailResponse saveRequest(@RequestPayload EmailRequest emailRequest) {
        return emailService.saveEmail(emailRequest);
    }
}
