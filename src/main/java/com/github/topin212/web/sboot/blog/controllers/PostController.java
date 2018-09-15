package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.web.sboot.blog.entities.BlogPost;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.services.PostService;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PublisherService publisherService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public PostController(PostService postService,
                          PublisherService publisherService) {
        this.postService = postService;
        this.publisherService = publisherService;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"page", "limit"}, produces = "application/json")
    public String getPostById(
            @RequestParam int page,
            @RequestParam int limit
    ) throws JsonProcessingException {

        System.out.println(page);
        System.out.println(limit);

        Page<BlogPost> allPostsInPageable = postService.getAllPostsInPageable(PageRequest.of(page, limit));

        return mapper.writeValueAsString(allPostsInPageable);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"page", "limit", "publisher_id"}, produces = "application/json")
    public String getPostById(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam("publisher_id") Publisher publisher
    ) throws JsonProcessingException {

        System.out.println(page);
        System.out.println(limit);
        System.out.println(publisher.getName());

        Page<BlogPost> allPostsInPageable = postService.
                getPostsByPublisherWithPageable(
                        publisher,
                        PageRequest.of(page, limit));

        return mapper.writeValueAsString(allPostsInPageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getPostById(@PathVariable("id") BlogPost blogPost) throws JsonProcessingException {
        return mapper.writeValueAsString(blogPost);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getAllPosts() throws JsonProcessingException {
        List<BlogPost> all = postService.getAllPosts();

        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    @RequestMapping(value = "/publisher", method = RequestMethod.GET)
    public ResponseEntity<String> getPostsByCurrentPublisher() throws JsonProcessingException {
        List<BlogPost> all = postService.getPostsByPublisher(getCurrentPublisher());

        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> createAPost(@RequestParam String postTitle, @RequestParam String postText) {
        BlogPost blogPost = new BlogPost();

        Publisher publisher = getCurrentPublisher();

        blogPost.setPublisher(publisher);
        blogPost.setPostTitle(postTitle);
        blogPost.setPostText(postText);
        blogPost.setPostDate(LocalDateTime.now());
        blogPost.setThumbsUpCount(0);

        postService.addOrUpdate(blogPost);
        return null;
    }

    @RequestMapping(value = "/own", method = RequestMethod.GET)
    public ResponseEntity<String> getOwnPosts() throws JsonProcessingException {
        List<BlogPost> postsByPublisher = postService.getPostsByPublisher(getCurrentPublisher());

        return new ResponseEntity<>(mapper.writeValueAsString(postsByPublisher), HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePost(
            @RequestParam Long postId,
            @RequestParam String postTitle,
            @RequestParam String postText
    ) {
        Publisher currentUser = getCurrentPublisher();

        BlogPost blogPost = postService.getPostById(postId);

        if (!currentUser.equals(blogPost.getPublisher())) {
            return new ResponseEntity<>("You are not authorized to edit this blogPost", HttpStatus.UNAUTHORIZED);
        }

        blogPost.setPostTitle(postTitle);
        blogPost.setPostText(postText);

        blogPost.setPostDate(LocalDateTime.now());

        postService.addOrUpdate(blogPost);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        BlogPost blogPost = postService.getPostById(postId);

        Publisher currentUser = getCurrentPublisher();

        if (!currentUser.equals(blogPost.getPublisher())) {
            return new ResponseEntity<>("You are not authorized to edit this blogPost", HttpStatus.UNAUTHORIZED);
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

    private Publisher getCurrentPublisher() {
        return publisherService.getPublisherByName(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @RequestMapping(value = "/getSelf")
    public String test() throws JsonProcessingException {
        return mapper.writeValueAsString(publisherService.getPublisherByName(
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}