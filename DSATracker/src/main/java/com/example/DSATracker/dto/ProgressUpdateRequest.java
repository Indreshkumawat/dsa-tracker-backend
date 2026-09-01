package com.example.DSATracker.dto;

public record ProgressUpdateRequest(
        Boolean isCompleted,
        Boolean isBookmarked,
        String notes
) {}
