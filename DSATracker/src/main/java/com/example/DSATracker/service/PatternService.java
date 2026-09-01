package com.example.DSATracker.service;

import com.example.DSATracker.dto.PatternDto;
import com.example.DSATracker.dto.QuestionDto;
import com.example.DSATracker.dto.SubPatternDto;
import com.example.DSATracker.repository.PatternRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatternService {

    private final PatternRepository patternRepository;

    public PatternService(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    @Transactional(readOnly = true)
    public List<PatternDto> getAllPatterns() {
        return patternRepository.findAll().stream()
                .map(this::mapToPatternDto)
                .collect(Collectors.toList());
    }

    // --- Private Mapping Helpers ---

    private PatternDto mapToPatternDto(Pattern pattern) {
        List<SubPatternDto> subPatternDtos = pattern.getSubPatterns().stream()
                .map(this::mapToSubPatternDto)
                .collect(Collectors.toList());

        return new PatternDto(
                pattern.getId(),
                pattern.getName(),
                pattern.getDescription(),
                subPatternDtos
        );
    }

    private SubPatternDto mapToSubPatternDto(SubPattern subPattern) {
        List<QuestionDto> questionDtos = subPattern.getQuestions().stream()
                .map(q -> new QuestionDto(q.getId(), q.getTitle(), q.getLeetcodeUrl(), q.getDifficulty()))
                .collect(Collectors.toList());

        return new SubPatternDto(
                subPattern.getId(),
                subPattern.getName(),
                questionDtos
        );
    }
}
