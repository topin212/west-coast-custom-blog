package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.web.sboot.blog.entities.BlogPost;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.entities.responseobjects.ProcedureStatusResponse;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import com.github.topin212.web.sboot.blog.services.PostService;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public ProcedureStatusResponse getPostsInPageable(
            @RequestParam int page,
            @RequestParam int limit
    ) {
        return new ProcedureStatusResponse(
                "postFetching",
                postService.getAllPostsInPageable(PageRequest.of(page, limit))
        );
    }

    @RequestMapping(value = "/own", method = RequestMethod.GET, produces = "application/json")
    public ProcedureStatusResponse getOwnPosts(
            @RequestParam int page,
            @RequestParam int limit
    ) {
        return new ProcedureStatusResponse(
                "postFetching", postService.getPostsByPublisherWithPageable(
                publisherService.getCurrentPublisher(), PageRequest.of(page, limit))
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ProcedureStatusResponse getPostById(@PathVariable("id") BlogPost blogPost) throws ApplicationException {
        if (blogPost != null && blogPost.isValid()) {
            return new ProcedureStatusResponse("postFetching", blogPost);
        }
        throw new ApplicationException("Invalid id.", "postFetching");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ProcedureStatusResponse getAllPosts() throws JsonProcessingException {
        List<BlogPost> all = postService.getAllPosts();
        return new ProcedureStatusResponse("postFetching", mapper.writeValueAsString(all));
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "application/json")
    public ProcedureStatusResponse createPost(@RequestBody BlogPost newPost) throws ApplicationException {
        Publisher publisher = publisherService.getCurrentPublisher();
        BlogPost blogPost = new BlogPost(publisher, newPost.getPostTitle(), newPost.getPostText());

        return new ProcedureStatusResponse(
                "postCreation",
                postService.addOrUpdate(blogPost)
        );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ProcedureStatusResponse updatePost(
            @PathVariable("id") BlogPost blogPost,
            @RequestBody BlogPost newBlogPost
    ) throws ApplicationException {
        Publisher currentUser = publisherService.getCurrentPublisher();

        if (!currentUser.equals(blogPost.getPublisher())) {
            throw new ApplicationException("You are not authorized to edit this blogPost", "postUpdating");
        }

        if (blogPost.isValid()) {
            return new ProcedureStatusResponse(
                    "postUpdating",
                    postService.editPost(blogPost, newBlogPost.getPostTitle(), newBlogPost.getPostText())
            );
        }

        throw new ApplicationException("The blog post was invalid", "postUpdating");

    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    public ProcedureStatusResponse deletePost(
            @PathVariable("postId") BlogPost blogPost
    ) throws ApplicationException {
        Publisher currentUser = publisherService.getCurrentPublisher();

        if (!currentUser.equals(blogPost.getPublisher())) {
            throw new ApplicationException("You are not authorized to edit this blogPost", "order_deletion");
        }

        postService.deactivatePost(blogPost);
        return new ProcedureStatusResponse("orderDeletion", "success");
    }

    @RequestMapping(value = "/thumbsup/{postid}", produces = "application/json")
    public ProcedureStatusResponse giveALike(@PathVariable("postid") Long postId) throws ApplicationException {
        if (postService.giveALike(postId)) {
            return new ProcedureStatusResponse("thumbsup", "success");
        }
        throw new ApplicationException("Invalid arguments provided", "thumbsup");
    }
}