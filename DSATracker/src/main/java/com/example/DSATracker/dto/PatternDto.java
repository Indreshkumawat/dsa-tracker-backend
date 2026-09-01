package com.example.DSATracker.dto;

import java.util.List;

public record PatternDto(
        Long id,
        String name,
        String description,
        List<SubPatternDto> subPatterns
) {}


