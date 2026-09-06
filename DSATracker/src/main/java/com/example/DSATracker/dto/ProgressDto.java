package com.example.DSATracker.dto;

public record ProgressDto(
        Long questionId,
        Boolean completed,
        Boolean revise,
        String notes
)