package com.github.topin212.web.sboot.blog.tests.services;

import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import com.github.topin212.web.sboot.blog.repositories.PublisherRepository;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import com.github.topin212.web.sboot.blog.services.TokenService;
import com.github.topin212.web.sboot.blog.tests.stubs.PublisherRepositoryStub;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PublisherServiceTest {

    private static PublisherRepository publisherRepository;
    private static PublisherService publisherService;

    @BeforeClass
    public static void setup(){
        publisherRepository = new PublisherRepositoryStub();
        publisherService = new PublisherService(publisherRepository,
                new BCryptPasswordEncoder(),
                new TokenService(publisherRepository));
    }


    @Test
    public void userRegisteredWithAllFieldsFilled_WhenSuppliedWithLoginAndPassword() throws ApplicationException {
        Publisher publisher = publisherService.registerPublisher("senya", "secure");

        assertThat(publisher.getName()).isEqualToIgnoringCase("senya");
        assertThat(publisher.getToken()).isNotEmpty();
        assertThat(publisher.getPasswordHash()).isNotEmpty();
    }

}
