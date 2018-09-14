package com.github.topin212.web.sboot.blog.services;

import com.github.topin212.web.sboot.blog.entities.Post;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    PostRepository postRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(Long postId){
        try{
            if(!postRepository.existsById(postId)){
                throw new IllegalArgumentException("Results for post id: " + postId + " not found");
            }
            Post post = postRepository.findById(postId).get();

            return post;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addPost(Post post){
        return postRepository.save(post) != null;
    }

    public boolean update(Post post){
        return postRepository.save(post) != null;
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public List<Post> getPostsByPublisher(Publisher publisher){
        return postRepository.findByPublisher(publisher);
    }

    //TODO refactor this
    public boolean giveALike(Long postId){
        try{
            if(!postRepository.existsById(postId)){
                throw new IllegalArgumentException("Results for post id: " + postId + " not found");
            }
            Post post = postRepository.findById(postId).get();

            post.giveALike();
            postRepository.save(post);

            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removePost(Post post){
        post.setDeleted(true);
        postRepository.save(post);
        return true;
    }

}
