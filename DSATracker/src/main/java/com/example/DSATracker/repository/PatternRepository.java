package com.example.DSATracker.repository;

import com.example.DSATracker.entity.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {
    // JpaRepository provides findAll() out of the box
}
