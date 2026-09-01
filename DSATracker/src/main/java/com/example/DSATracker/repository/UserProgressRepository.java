package com.example.DSATracker.repository;

import com.example.DSATracker.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    // Finds the specific progress record for a user and a question
    Optional<UserProgress> findByUserIdAndQuestionId(Long userId, Long questionId);
}