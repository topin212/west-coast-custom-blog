package com.github.topin212.web.sboot.blog.services;

import com.github.topin212.web.sboot.blog.config.auth.JwtLikeToken;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public PublisherService(
            PublisherRepository publisherRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.publisherRepository = publisherRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public Publisher getPublisherByName(String name){
        return publisherRepository.findByName(name);
    }

    public Publisher registerPublisher(String name, String password){
        String encodedPassword = passwordEncoder.encode(password);
        Publisher publisher = new Publisher(name, encodedPassword);

        publisherRepository.save(publisher);
        tokenService.generateToken(publisher.getName(), encodedPassword, JwtLikeToken.getDefault());

        return publisher;
    }

    public Publisher getCurrentPublisher() {
        return getPublisherByName(
                SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
