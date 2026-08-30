package com.example.springproject.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "NOTES")
public class Note {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String data;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Note() {
    }

    public Note(String data, User user) {
        this.data = data;
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
