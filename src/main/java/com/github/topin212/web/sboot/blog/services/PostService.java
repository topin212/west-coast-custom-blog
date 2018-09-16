package com.github.topin212.web.sboot.blog.services;

import com.github.topin212.web.sboot.blog.entities.BlogPost;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public BlogPost addOrUpdate(BlogPost blogPost){
        return postRepository.save(blogPost);
    }

    public BlogPost editPost(BlogPost original, String newPostTitle, String newPostText){
        original.setPostTitle(newPostTitle);
        original.setPostText(newPostText);
        original.setPostDate(LocalDateTime.now());

        return addOrUpdate(original);
    }

    public List<BlogPost> getAllPosts(){
        return postRepository.findAll();
    }

    public Page<BlogPost> getAllPostsInPageable(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    public List<BlogPost> getPostsByPublisher(Publisher publisher){
        return postRepository.findByPublisher(publisher);
    }

    public Page<BlogPost> getPostsByPublisherWithPageable(Publisher publisher, Pageable pageable){
        return postRepository.findByPublisher(publisher, pageable);
    }

    public boolean giveALike(Long postId){
        if(!postRepository.existsById(postId)){
            throw new IllegalArgumentException("Results for blogPost id: " + postId + " not found");
        }

        BlogPost blogPost = postRepository.findById(postId).get();
        blogPost.giveALike();
        postRepository.save(blogPost);

        return true;
    }

    public void deactivatePost(BlogPost blogPost){
        blogPost.setActive(false);
        postRepository.save(blogPost);
    }
}
