package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.web.sboot.blog.entities.BlogPost;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import com.github.topin212.web.sboot.blog.services.PostService;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class BlogPostController {

    private final PostService postService;
    private final PublisherService publisherService;

    private final ObjectMapper mapper;

    @Autowired
    public BlogPostController(
            PostService postService,
            PublisherService publisherService,
            ObjectMapper mapper
    ) {
        this.postService = postService;
        this.publisherService = publisherService;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"page", "limit"}, produces = "application/json")
    public Page<BlogPost> getPostById(
            @RequestParam int page,
            @RequestParam int limit
    ) throws JsonProcessingException {
        return postService.getAllPostsInPageable(PageRequest.of(page, limit));
    }

    @RequestMapping(method = RequestMethod.GET, params = {"page", "limit", "publisher_id"}, produces = "application/json")
    public Page<BlogPost> getPostById(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam("publisher_id") Publisher publisher
    ) throws JsonProcessingException {
        return postService.
                getPostsByPublisherWithPageable(publisher, PageRequest.of(page, limit));
    }

    @RequestMapping(value = "/own", method = RequestMethod.GET, produces = "application/json")
    public Page<BlogPost> getOwnPosts(
            @RequestParam int page,
            @RequestParam int limit
    ) throws JsonProcessingException {
        return postService.getPostsByPublisherWithPageable(
                publisherService.getCurrentPublisher(), PageRequest.of(page, limit));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public BlogPost getPostById(@PathVariable("id") BlogPost blogPost) throws JsonProcessingException {
        return blogPost;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getAllPosts() throws JsonProcessingException {
        List<BlogPost> all = postService.getAllPosts();

        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    @RequestMapping(value = "/publisher", method = RequestMethod.GET)
    public ResponseEntity<String> getPostsByCurrentPublisher() throws JsonProcessingException {
        List<BlogPost> all = postService.getPostsByPublisher(publisherService.getCurrentPublisher());

        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    //Todo write a private method to
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    public BlogPost createAPost(@RequestBody BlogPost newPost) {
        Publisher publisher = publisherService.getCurrentPublisher();
        BlogPost blogPost = new BlogPost(publisher, newPost.getPostTitle(), newPost.getPostText());

        return postService.addOrUpdate(blogPost);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public BlogPost updatePost(
            @PathVariable("id") BlogPost blogPost,
            @RequestBody BlogPost newBlogPost
    ) throws ApplicationException {
        Publisher currentUser = publisherService.getCurrentPublisher();

        if (!currentUser.equals(blogPost.getPublisher())) {
            throw new ApplicationException("You are not authorized to edit this blogPost");
        }

        return postService.editPost(blogPost, newBlogPost.getPostTitle(), newBlogPost.getPostText());
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePost(
            @PathVariable("postId") BlogPost blogPost
    ) throws ApplicationException {
        Publisher currentUser = publisherService.getCurrentPublisher();

        if (!currentUser.equals(blogPost.getPublisher())) {
            throw new ApplicationException("You are not authorized to edit this blogPost");
        }

        postService.deactivatePost(blogPost);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @RequestMapping(value = "/thumbsup/{postid}", produces = "application/json")
    public ResponseEntity<String> giveALike(@PathVariable("postid") Long postId) {
        if (postService.giveALike(postId)) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }
}