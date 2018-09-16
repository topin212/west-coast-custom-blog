package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.entities.responseobjects.TokenResponse;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
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

    private final ObjectMapper mapper;

    @Autowired
    public LoginController(
            TokenService tokenService,
            PublisherService publisherService,
            ObjectMapper mapper
    ) {
        this.tokenService = tokenService;
        this.publisherService = publisherService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(
            @RequestParam String login,
            @RequestParam String password
    ) throws JsonProcessingException, ApplicationException {

        Publisher publisher = publisherService.getPublisherByName(login);

        tokenService.revokeToken(publisher.getToken());
        String token = tokenService.generateToken(login, password, SecurityContextHolder.getContext().getAuthentication());

        TokenResponse response = new TokenResponse(token, publisher);
        return new ResponseEntity<>(mapper.writeValueAsString(response), HttpStatus.OK);
    }
}
