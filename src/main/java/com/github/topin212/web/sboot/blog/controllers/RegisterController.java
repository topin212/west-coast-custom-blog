package com.github.topin212.web.sboot.blog.controllers;

import com.github.topin212.web.sboot.blog.entities.responseobjects.ProcedureStatusResponse;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import com.github.topin212.web.sboot.blog.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    private final PublisherService publisherService;

    @Autowired
    public RegisterController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ProcedureStatusResponse register(@RequestParam String login, @RequestParam String password) throws ApplicationException {
        return new ProcedureStatusResponse("login", publisherService.registerPublisher(login, password));
    }
}
