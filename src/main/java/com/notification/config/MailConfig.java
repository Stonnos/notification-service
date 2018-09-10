package com.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Email configuration.
 *
 * @author Roman Batygin
 */
@Data
@ConfigurationProperties("mailConfig")
public class MailConfig {

    /**
     * Number of failed attempts to send experiment results
     */
    private Integer maxFailedAttemptsToSent;

    /**
     * Page size for emails sending
     */
    private Integer pageSize;
}