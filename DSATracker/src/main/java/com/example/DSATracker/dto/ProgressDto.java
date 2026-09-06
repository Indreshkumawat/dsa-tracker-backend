package com.example.DSATracker.dto;

import java.time.LocalDate;

public record ProgressDto(
        Long questionId,
        Boolean completed,
     //   Boolean revise,
        String userDifficulty,
        String notes,
        LocalDate completedAt
){}