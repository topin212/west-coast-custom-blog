package com.github.topin212.web.sboot.blog.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JwtLikeToken extends PreAuthenticatedAuthenticationToken {

    private static JwtLikeToken instance;

    public JwtLikeToken(Object aPrincipal, Object aCredentials) {
        super(aPrincipal, aCredentials);
    }

    public JwtLikeToken(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public JwtLikeToken setToken(String token){
        setDetails(token);
        return this;
    }

    public String getToken(){
        return (String) getDetails();
    }

    public static JwtLikeToken getDefaultValue(){

        if(instance != null){
            return instance;
        }

        Set<GrantedAuthority> roleSet = new HashSet<>();
        roleSet.add(new SimpleGrantedAuthority("ROLE_PUBLISHER"));

        instance = new JwtLikeToken("",
                null,
                roleSet);

        return instance;
    }
}