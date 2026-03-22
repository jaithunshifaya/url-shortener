package com.urlShortener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String originalUrl;

    @Column(unique = true)
    private String shortCode;

    private int clickCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}