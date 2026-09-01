package com.example.DSATracker.dto;

import java.util.List;

public record QuestionDto(
        Long id,
        String title,
        String leetcodeUrl,
        String difficulty
) {}

