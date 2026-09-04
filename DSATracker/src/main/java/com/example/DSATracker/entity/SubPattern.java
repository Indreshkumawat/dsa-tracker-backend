package com.example.DSATracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class SubPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer orderNum;

    // Many SubPatterns belong to one Pattern
    @ManyToOne
    @JoinColumn(name = "pattern_id")
    @JsonBackReference
    private Pattern pattern;

    // A SubPattern has many Questions
    @OneToMany(mappedBy = "subPattern", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Question> questions;

    // Getters and Setters
}