package com.github.topin212.web.sboot.blog.repositories;


import com.github.topin212.web.sboot.blog.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    List<Publisher> findAll();

    Publisher findByName(String name);
}
