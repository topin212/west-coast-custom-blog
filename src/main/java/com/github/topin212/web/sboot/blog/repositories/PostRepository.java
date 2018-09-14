package com.github.topin212.web.sboot.blog.repositories;

import com.github.topin212.web.sboot.blog.entities.Post;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    List<Post> findAll();

    List<Post> findByPublisher(Publisher publisher);
}
