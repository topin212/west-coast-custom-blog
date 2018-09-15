package com.github.topin212.web.sboot.blog.controllers;

import com.github.topin212.web.sboot.blog.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    private final PublisherService publisherService;

    @Autowired
    public RegisterController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestParam String login, @RequestParam String password){
        publisherService.registerPublisher(login, password);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
