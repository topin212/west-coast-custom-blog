package com.github.topin212.web.sboot.blog.controllers;

import com.github.topin212.web.sboot.blog.entities.Publisher;
import com.github.topin212.web.sboot.blog.entities.responseobjects.ProcedureStatusResponse;
import com.github.topin212.web.sboot.blog.entities.responseobjects.TokenResponse;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import com.github.topin212.web.sboot.blog.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final TokenService tokenService;
    private final PublisherService publisherService;

    @Autowired
    public LoginController(
            TokenService tokenService,
            PublisherService publisherService
    ) {
        this.tokenService = tokenService;
        this.publisherService = publisherService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ProcedureStatusResponse login(
            @RequestParam String login,
            @RequestParam String password
    ) throws ApplicationException {

        Publisher publisher = publisherService.getPublisherByName(login);

        tokenService.revokeToken(publisher.getToken());
        String token = tokenService.generateToken(login, password, SecurityContextHolder.getContext().getAuthentication());

        return new ProcedureStatusResponse(
                "login", new TokenResponse(token, publisher)
        );
    }
}
