package com.example.DSATracker.service;

import com.example.DSATracker.dto.ProgressDto;
import com.example.DSATracker.entity.Question;
import com.example.DSATracker.entity.User;
import com.example.DSATracker.entity.UserProgress;
import com.example.DSATracker.exception.ResourceNotFoundException;
import com.example.DSATracker.repository.QuestionRepository;
import com.example.DSATracker.repository.UserProgressRepository;
import com.example.DSATracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    // New GET method to supply the frontend on load
    @Transactional(readOnly = true)
    public List<ProgressDto> getUserProgress(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return progressRepository.findByUserId(user.getId()).stream()
                .map(p -> new ProgressDto(
                        p.getQuestion().getId(),
                        p.isCompleted(),
                        //p.isBookmarked(), // Maps DB field to frontend 'revise' state
                        p.getUserDifficulty(),
                        p.getNotes(),
                        p.getCompletedAt()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProgress(String email, ProgressDto request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        UserProgress progress = progressRepository.findByUserIdAndQuestionId(user.getId(), question.getId())
                .orElseGet(() -> {
                    UserProgress newProgress = new UserProgress();
                    newProgress.setUser(user);
                    newProgress.setQuestion(question);
                    return newProgress;
                });

        if (request.completed() != null) {
            progress.setCompleted(request.completed());
            if (request.completed()) {
                progress.setCompletedAt(java.time.LocalDate.now());
            } else {
                progress.setCompletedAt(null);
            }
        }
       // if (request.revise() != null) progress.setBookmarked(request.revise());
        if (request.userDifficulty() != null) {
            if (request.userDifficulty().equals("NONE")) {
                progress.setUserDifficulty(null); // Clears it if they cycle past Hard
            } else {
                progress.setUserDifficulty(request.userDifficulty());
            }
        }
        if (request.notes() != null) progress.setNotes(request.notes());

        progressRepository.save(progress);
    }
}