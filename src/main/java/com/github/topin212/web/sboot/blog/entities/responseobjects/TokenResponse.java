package com.github.topin212.web.sboot.blog.entities.responseobjects;

import com.github.topin212.web.sboot.blog.entities.Publisher;

public class TokenResponse {
    String token;
    Publisher publisher;

    public TokenResponse(String token, Publisher publisher) {
        this.token = token;
        this.publisher = publisher;
    }
}
