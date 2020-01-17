package com.notification;

import com.notification.model.Email;
import com.notification.repository.EmailRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Abstract class with JPA test configuration.
 *
 * @author Roman Batygin
 */
@RunWith(SpringRunner.class)
@AutoConfigureDataJpa
@EnableJpaRepositories(basePackageClasses = EmailRepository.class)
@EntityScan(basePackageClasses = Email.class)
@EnableConfigurationProperties
@TestPropertySource("classpath:application.properties")
public abstract class AbstractJpaTest {

    @Before
    public final void before() {
        deleteAll();
        init();
    }

    @After
    public final void after() {
        deleteAll();
    }

    public void init() {
    }

    public void deleteAll() {
    }
}
