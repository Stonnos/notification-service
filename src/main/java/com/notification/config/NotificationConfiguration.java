package com.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Notification configuration.
 *
 * @author Roman Batygin
 */
@Configuration
@EnableScheduling
public class NotificationConfiguration {

    /**
     * Creates mail config bean.
     *
     * @return mail config bean
     */
    @Bean
    public MailConfig mailConfig() {
        return new MailConfig();
    }
}
