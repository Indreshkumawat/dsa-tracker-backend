package com.example.DSATracker.repository;

import com.example.DSATracker.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    Optional<UserProgress> findByUserIdAndQuestionId(Long userId, Long questionId);

    // New method to fetch all progress for the frontend load
    List<UserProgress> findByUserId(Long userId);
}