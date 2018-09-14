package com.github.topin212.web.sboot.blog.services;

import com.github.topin212.web.sboot.blog.entities.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    PublisherService publisherService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Publisher p = publisherService.getPublisherByName(login);

        if(p == null) {
            throw new UsernameNotFoundException("User " + login + " is not known.");
        }

        Set<GrantedAuthority> roles = new HashSet<>();

        roles.add(new SimpleGrantedAuthority("ROLE_PUBLISHER"));

        return new User(p.getName(), p.getPasswordHash(), roles);
    }
}
