package com.github.topin212.web.sboot.blog.tests.stubs;

import com.github.topin212.web.sboot.blog.entities.BlogPost;
import com.github.topin212.web.sboot.blog.repositories.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogPostRepositoryStub extends AbstractRepositoryStub<BlogPost> implements PostRepository {
    public BlogPostRepositoryStub() {
        this.storage = new ArrayList<>();
        PublisherRepositoryStub publisherRepositoryStub = new PublisherRepositoryStub();

        BlogPost post1 = new BlogPost(
                publisherRepositoryStub.findById(1L).get(),
                "petay wrote this",
                "the most beautiful thing");
        post1.setId(1L);

        BlogPost post2 = new BlogPost(
                publisherRepositoryStub.findById(1L).get(),
                "petay wrote this another time",
                "the most beautiful thing number 2");
        post2.setId(2L);

        BlogPost post3 = new BlogPost(
                publisherRepositoryStub.findById(1L).get(),
                "vasay wrote this",
                "not exactly the most beautiful thing");
        post3.setId(3L);

        storage.add(post1);
        storage.add(post2);
        storage.add(post3);
    }

    @Override
    public BlogPost save(BlogPost s) {
        boolean contains = storage.stream()
                .anyMatch(blogPost -> blogPost.getId() == s.getId());

        if(contains){
            BlogPost blogPost = storage.stream().filter(post -> s.getId() == post.getId()).findFirst().get();
            blogPost.setThumbsUpCount(s.getThumbsUpCount());

            return blogPost;
        }

        storage.add(s);
        return s;
    }

}
