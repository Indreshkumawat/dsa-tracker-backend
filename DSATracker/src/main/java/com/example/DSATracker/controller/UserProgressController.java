package com.example.DSATracker.controller;

import com.example.DSATracker.dto.ProgressDto;
import com.example.DSATracker.service.UserProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress") // Updated to match frontend Axios calls
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserProgressController {

    private final UserProgressService progressService;

    public UserProgressController(UserProgressService progressService) {
        this.progressService = progressService;
    }

    // Handles the initial data load on page refresh
    @GetMapping
    public ResponseEntity<List<ProgressDto>> getProgress(Authentication authentication) {
        String userEmail = authentication.getName();
        List<ProgressDto> progressList = progressService.getUserProgress(userEmail);
        return ResponseEntity.ok(progressList);
    }

    // Handles the checkbox clicks, note saves, and revise toggles
    @PostMapping
    public ResponseEntity<Void> updateQuestionProgress(
            @RequestBody ProgressDto request,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        progressService.updateProgress(userEmail, request);
        return ResponseEntity.ok().build();
    }
}