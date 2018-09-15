package com.github.topin212.web.sboot.blog.repositories;

import com.github.topin212.web.sboot.blog.entities.BlogPost;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPost> findAll();

    Page<BlogPost> findAll(Pageable pageable);

    List<BlogPost> findByPublisher(Publisher publisher);

    Page<BlogPost> findByPublisher(Publisher publisher, Pageable pageable);
}
