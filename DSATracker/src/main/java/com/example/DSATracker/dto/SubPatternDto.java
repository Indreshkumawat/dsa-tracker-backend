package com.example.DSATracker.dto;

import java.util.List;

public record SubPatternDto(
        Long id,
        String name,
        List<QuestionDto> questions
) {}
