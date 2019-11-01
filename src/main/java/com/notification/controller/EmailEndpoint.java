package com.notification.controller;

import com.notification.dto.EmailRequest;
import com.notification.dto.EmailResponse;
import com.notification.dto.ResponseStatus;
import com.notification.mapping.EmailRequestMapper;
import com.notification.model.Email;
import com.notification.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.notification.util.Utils.buildResponse;
import static com.notification.util.Utils.validateEmailRequest;

/**
 * Email endpoint.
 */
@Slf4j
@Endpoint
@RequiredArgsConstructor
public class EmailEndpoint {

    private static final String NAMESPACE_URI = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String EMAIL_REQUEST_LOCAL_PART = "emailRequest";

    private final EmailRepository emailRepository;
    private final EmailRequestMapper emailRequestMapper;

    /**
     * Saves email request.
     *
     * @param emailRequest - email request
     * @return email response
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = EMAIL_REQUEST_LOCAL_PART)
    @ResponsePayload
    public EmailResponse saveRequest(@RequestPayload EmailRequest emailRequest) {
        ResponseStatus responseStatus = ResponseStatus.SUCCESS;
        String uuid = UUID.randomUUID().toString();
        log.info("Received email request with uuid '{}'.", uuid);
        if (!validateEmailRequest(emailRequest)) {
            log.warn("Email request with uuid '{}' is invalid!", uuid);
            responseStatus = ResponseStatus.INVALID_REQUEST_PARAMS;
        } else {
            try {
                Email email = emailRequestMapper.map(emailRequest);
                email.setUuid(uuid);
                email.setSaveDate(LocalDateTime.now());
                emailRepository.save(email);
                log.info("Email request with uuid '{}' has been saved.", uuid);
            } catch (Exception ex) {
                log.error("There was an error while saving email with uuid '{}': {}", uuid, ex.getMessage());
                responseStatus = ResponseStatus.ERROR;
            }
        }
        return buildResponse(uuid, responseStatus);
    }
}
