package com.example.DSATracker.controller;


import com.example.DSATracker.dto.ProgressUpdateRequest;
import com.example.DSATracker.service.UserProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/progress")
public class UserProgressController {

    private final UserProgressService progressService;

    public UserProgressController(UserProgressService progressService) {
        this.progressService = progressService;
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Void> updateQuestionProgress(
            @PathVariable Long questionId,
            @RequestBody ProgressUpdateRequest request,
            Authentication authentication
    ) {
        // The email is stored in the Principal during the JwtAuthenticationFilter step
        String userEmail = authentication.getName();

        progressService.updateProgress(userEmail, questionId, request);

        return ResponseEntity.ok().build();
    }
}