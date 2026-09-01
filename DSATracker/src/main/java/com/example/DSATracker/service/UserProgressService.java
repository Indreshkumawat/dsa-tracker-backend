package com.example.DSATracker.service;

import com.example.DSATracker.dto.ProgressUpdateRequest;
import com.example.DSATracker.entity.Question;
import com.example.DSATracker.entity.User;
import com.example.DSATracker.entity.UserProgress;
import com.example.DSATracker.exception.ResourceNotFoundException;
import com.example.DSATracker.repository.QuestionRepository;
import com.example.DSATracker.repository.UserProgressRepository;
import com.example.DSATracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProgressService {

    private final UserProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public UserProgressService(UserProgressRepository progressRepository,
                               UserRepository userRepository,
                               QuestionRepository questionRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void updateProgress(String email, Long questionId, ProgressUpdateRequest request) {
        // 1. Fetch the user and the question
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question with ID " + questionId + " not found"));
        // 2. Find existing progress, or create a new one if it doesn't exist
        UserProgress progress = progressRepository.findByUserIdAndQuestionId(user.getId(), questionId)
                .orElseGet(() -> {
                    UserProgress newProgress = new UserProgress();
                    newProgress.setUser(user);
                    newProgress.setQuestion(question);
                    return newProgress;
                });

        // 3. Update only the fields that were provided in the request
        if (request.isCompleted() != null) {
            progress.setCompleted(request.isCompleted());
        }
        if (request.isBookmarked() != null) {
            progress.setBookmarked(request.isBookmarked());
        }
        if (request.notes() != null) {
            progress.setNotes(request.notes());
        }

        // 4. Save back to PostgreSQL
        progressRepository.save(progress);
    }
}