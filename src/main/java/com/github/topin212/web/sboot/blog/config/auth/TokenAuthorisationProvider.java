package com.github.topin212.web.sboot.blog.config.auth;

import com.github.topin212.web.sboot.blog.services.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class TokenAuthorisationProvider implements AuthenticationProvider {

    private final TokenService tokenService;

    public TokenAuthorisationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();

        if (token == null && token.isEmpty()) {
            throw new BadCredentialsException("Token is empty or null.");
        }

        if (!tokenService.containsToken(token)) {
            throw new BadCredentialsException("The token was invalid: " + token);
        }

        return JwtLikeToken.getDefault().setToken(token);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
