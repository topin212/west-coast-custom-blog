package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.web.sboot.blog.entities.Post;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.services.PostService;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
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

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String getPostById(@PathVariable Long id) throws JsonProcessingException {
        Post postById = postService.getPostById(id);

        return mapper.writeValueAsString(postById);
    }

    //TODO add pagination
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<String> getAllPosts() throws JsonProcessingException {
        List<Post> all = postService.getAllPosts();

        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/publisher", method = RequestMethod.GET)
    public ResponseEntity<String> getPostsByCurrentPublisher() throws JsonProcessingException {
        List<Post> all = postService.getPostsByPublisher(getCurrentPublisher());

        return new ResponseEntity<>(mapper.writeValueAsString(all), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<String> createAPost(@RequestParam String postTitle, @RequestParam String postText){
        Post post = new Post();

        Publisher publisher = getCurrentPublisher();

        post.setPublisher(publisher);
        post.setPostTitle(postTitle);
        post.setPostText(postText);
        post.setPostDate(LocalDateTime.now());
        post.setLikeCount(0);

        postService.addPost(post);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/own", method = RequestMethod.GET)
    public ResponseEntity<String> getOwnPosts() throws JsonProcessingException {

        List<Post> postsByPublisher = postService.getPostsByPublisher(getCurrentPublisher());

        return new ResponseEntity<>(mapper.writeValueAsString(postsByPublisher), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public ResponseEntity<String> updatePost(@RequestParam Long postId,
                                             @RequestParam String postTitle,
                                             @RequestParam String postText){
        Publisher currentUser = getCurrentPublisher();

        Post post = postService.getPostById(postId);

        if(!currentUser.equals(post.getPublisher())){
            return new ResponseEntity<>("You are not authorized to edit this post", HttpStatus.UNAUTHORIZED);
        }

        post.setPostTitle(postTitle);
        post.setPostText(postText);

        post.setPostDate(LocalDateTime.now());

        postService.update(post);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/remove/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        Post post = postService.getPostById(postId);

        Publisher currentUser = getCurrentPublisher();

        if(!currentUser.equals(post.getPublisher())){
            return new ResponseEntity<>("You are not authorized to edit this post", HttpStatus.UNAUTHORIZED);
        }

        postService.removePost(post);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/thumbsup/{postid}")
    public ResponseEntity<String> giveALike(@PathVariable("postid") Long postId){
        if(postService.giveALike(postId))
            return new ResponseEntity<>("success", HttpStatus.OK);
        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
    }

    private Publisher getCurrentPublisher(){
        return publisherService.getPublisherByName(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}