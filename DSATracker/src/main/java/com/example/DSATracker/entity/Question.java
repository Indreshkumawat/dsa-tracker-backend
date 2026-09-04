package com.example.DSATracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String leetcodeUrl;

    private String difficulty; // "Easy", "Medium", "Hard"

    // Many Questions belong to one SubPattern
    @ManyToOne
    @JoinColumn(name = "sub_pattern_id")
    @JsonBackReference
    private SubPattern subPattern;

    // Getters and Setters
}