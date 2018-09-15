package com.github.topin212.web.sboot.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "publisher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "pass_hash")
    private String passwordHash;

    @Column(name = "reg_date")
    private LocalDateTime registrationDate;

    @Column(name = "role_id")
    private Long role_id;

    @Column
    private String token;

    public Publisher() {}

    public Publisher(String name, String password) {
        this.name = name;
        this.passwordHash = password;
        this.setRegistrationDate(LocalDateTime.now());
        this.role_id = 1L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}