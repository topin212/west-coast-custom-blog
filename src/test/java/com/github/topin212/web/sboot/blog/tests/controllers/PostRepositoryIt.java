package com.github.topin212.web.sboot.blog.tests.controllers;


import com.github.topin212.web.sboot.blog.entities.Post;
import com.github.topin212.web.sboot.blog.repositories.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryIt {

    @Autowired
    TestEntityManager tem;

    @Autowired
    PostRepository postRepository;

    @Test
    public void leaveAlikeShouldIncrementLikeCount(){
        Post post = postRepository.findById(1L).get();

        long initialLikeCount = post.getLikeCount();

        post.giveALike();

        assertThat(post.getLikeCount())
                .isEqualTo(initialLikeCount + 1);
    }

}
