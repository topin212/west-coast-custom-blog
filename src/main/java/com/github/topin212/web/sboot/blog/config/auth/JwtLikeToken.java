package com.github.topin212.web.sboot.blog.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JwtLikeToken extends PreAuthenticatedAuthenticationToken {

    private JwtLikeToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    JwtLikeToken setToken(String token) {
        setDetails(token);
        return this;
    }

    public String getToken() {
        return (String) getDetails();
    }

    public static JwtLikeToken getDefault() {

        Set<GrantedAuthority> roleSet = new HashSet<>();
        roleSet.add(new SimpleGrantedAuthority("ROLE_PUBLISHER"));

        return new JwtLikeToken("", null, roleSet);
    }
}