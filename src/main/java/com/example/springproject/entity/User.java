package com.example.springproject.entity;

import jakarta.persistence.*;
// each user object equals a row in the USERS  table
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="USER_EMAIL", length=50, nullable=false, unique=true)
    private String email;

    @Column(name="USER_NAME", length=50, nullable=false, unique=false)
    private String name;

    @Column(name="USER_ROLE", length=50, nullable=false, unique=false)
    private String role;

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }
}
