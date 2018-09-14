package com.github.topin212.web.sboot.blog.tests.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.services.PostService;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostControllerIt {

    @Autowired
    TestEntityManager em;

    @Autowired
    PublisherService publisherService;

    @Autowired
    PostService postService;

    @Test
    public void testUserPetyaShouldHaveTwoPosts(){

    Publisher petya = publisherService.getPublisherByName("Petya");
        assertThat(postService.getPostsByPublisher(petya).size()).isEqualTo(2);
    }
}
