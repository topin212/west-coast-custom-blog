package com.github.topin212.web.sboot.blog.services;

import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenService {

    @Autowired
    PublisherRepository publisherRepository;

    public String generateToken(String username, String password, Authentication auth){
        String token = username + "#" + UUID.randomUUID().toString();

        System.out.println("added token " + token + " to storage");

        Publisher publisher = publisherRepository.findByName(username);
        publisher.setToken(token);

        publisherRepository.save(publisher);

        return token;
    }

    public void revokeToken(String token){
        String username = token.split("#")[0];

        Publisher publisher = publisherRepository.findByName(username);
        publisher.setToken("");

        publisherRepository.save(publisher);
    }

    public boolean containsToken(String token){
        return publisherRepository.findAll().stream().anyMatch(p -> p.getToken().equals(token));
    }
}
