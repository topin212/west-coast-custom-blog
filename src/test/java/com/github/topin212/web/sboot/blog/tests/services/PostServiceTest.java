package com.github.topin212.web.sboot.blog.tests.services;

import com.github.topin212.web.sboot.blog.entities.BlogPost;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import com.github.topin212.web.sboot.blog.services.PostService;
import com.github.topin212.web.sboot.blog.tests.stubs.BlogPostRepositoryStub;
import com.github.topin212.web.sboot.blog.tests.stubs.PublisherRepositoryStub;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
public class PostServiceTest {

    private static BlogPostRepositoryStub blogPostRepositoryStub;

    static PostService postService;

    @BeforeClass
    public static void setup(){
        blogPostRepositoryStub = new BlogPostRepositoryStub();
        postService = new PostService(blogPostRepositoryStub);
    }

    @Test
    public void giveALike_shouldIncreasePostThumbsUpCount(){
        postService.giveALike(1L);
        Long actual = blogPostRepositoryStub.findById(1L).get().getThumbsUpCount();

        assertThat(actual).isEqualTo(1L);
    }

    @Test
    public void giveALike_shouldFail_WhenInvalidIdProvided() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> postService.giveALike(20L));
    }

    @Test
    public void addOrUpdate_shouldFail_WhenInvalidBlogPostProvided(){
        BlogPost blogPost = new BlogPost();

        assertThatExceptionOfType(ApplicationException.class)
                .isThrownBy(() -> postService.addOrUpdate(blogPost));
    }

}
