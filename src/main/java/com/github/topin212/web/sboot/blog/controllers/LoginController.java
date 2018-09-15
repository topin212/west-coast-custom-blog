package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.entities.responseobjects.TokenResponse;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import com.github.topin212.web.sboot.blog.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final TokenService tokenService;
    private final PublisherService publisherService;

    ObjectMapper mapper;

    @Autowired
    public LoginController(TokenService tokenService, PublisherService publisherService) {
        this.tokenService = tokenService;
        this.publisherService = publisherService;
        this.mapper = new ObjectMapper();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam String login, @RequestParam String password) throws JsonProcessingException {
        Publisher publisher = publisherService.getPublisherByName(login);

        String token = tokenService.generateToken(login, password, SecurityContextHolder.getContext().getAuthentication());

        if(publisher.getToken() == null || publisher.getToken().isEmpty()){
            publisher.setToken(token);
        }

        String oldToken = publisher.getToken();
        tokenService.revokeToken(oldToken);

        publisher.setToken(token);

        publisherService.addOrUpdate(publisher);

        TokenResponse response = new TokenResponse(token);
        return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.OK);
    }
}
