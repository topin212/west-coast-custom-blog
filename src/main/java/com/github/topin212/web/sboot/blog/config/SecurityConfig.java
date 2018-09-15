package com.github.topin212.web.sboot.blog.config;

import com.github.topin212.web.sboot.blog.config.auth.JwtLikeFilter;
import com.github.topin212.web.sboot.blog.config.auth.TokenAuthorisationProvider;
import com.github.topin212.web.sboot.blog.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder
                .userDetailsService(userDetailsService)
                .and()
                .authenticationProvider(authenticationProvider())
                .authenticationProvider(tokenAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/register", "/login").permitAll()
            .anyRequest().hasRole("PUBLISHER")
            .and()
            .logout().permitAll()

            .and()
            .addFilterBefore(new JwtLikeFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder result = new BCryptPasswordEncoder();

        System.out.println(result.encode("test"));

        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenService tokenService(){
        return new TokenService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public TokenAuthorisationProvider tokenAuthenticationProvider(){
        return new TokenAuthorisationProvider(tokenService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
