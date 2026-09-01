package com.example.DSATracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "leetcode_url", nullable = false)
    private String leetcodeUrl;

    @Column(nullable = false)
    private String difficulty; // "Easy", "Medium", "Hard"

    // Many Questions belong to one SubPattern
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_pattern_id", nullable = false)
    private SubPattern subPattern;

    // Getters and Setters
}