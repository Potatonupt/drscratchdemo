package com.example.demo.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
public class AnalysisResult {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String result;

    // getters and setters

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }
}
