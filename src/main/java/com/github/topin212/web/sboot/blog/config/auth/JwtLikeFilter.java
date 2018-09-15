package com.github.topin212.web.sboot.blog.config.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtLikeFilter extends GenericFilterBean {

    AuthenticationManager authManager;

    public JwtLikeFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String token = request.getHeader("Token");

        if(token != null && !token.equals("")){
            authForToken(token);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }


    private void authForToken(String token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);

        Authentication responseAuthentication = authManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
    }
}