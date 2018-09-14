package com.github.topin212.web.sboot.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam String login, @RequestParam String password){

        if(login.equals("test")){
            return new ResponseEntity<>("success", HttpStatus.OK);
        }

        return new ResponseEntity<>("fail", HttpStatus.UNAUTHORIZED);
    }
}
