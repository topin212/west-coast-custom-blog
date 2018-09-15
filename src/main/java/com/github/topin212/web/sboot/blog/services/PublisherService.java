package com.github.topin212.web.sboot.blog.services;

import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PublisherService(
            PublisherRepository publisherRepository,
            PasswordEncoder passwordEncoder) {
        this.publisherRepository = publisherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Publisher getPublisherByName(String name){
        return publisherRepository.findByName(name);
    }

    public void registerPublisher(String name, String password){
        String encodedPassword = passwordEncoder.encode(password);

        Publisher publisher = new Publisher(name, encodedPassword);

        publisherRepository.save(publisher);
    }

    public void addOrUpdate(Publisher publisher){
        publisherRepository.save(publisher);
    }
}
