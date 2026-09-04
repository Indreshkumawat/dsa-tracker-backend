package com.example.DSATracker.config;


import com.example.DSATracker.entity.Pattern;
import com.example.DSATracker.entity.Question;
import com.example.DSATracker.entity.SubPattern;
import com.example.DSATracker.repository.PatternRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PatternRepository patternRepository;
    private final ObjectMapper objectMapper;

    public DataInitializer(PatternRepository patternRepository, ObjectMapper objectMapper) {
        this.patternRepository = patternRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (patternRepository.count() == 0) {
            System.out.println("⏳ Database empty. Loading patterns.json...");
            try (InputStream inputStream = new ClassPathResource("data/patterns.json").getInputStream()) {

                List<Pattern> patterns = objectMapper.readValue(inputStream, new TypeReference<List<Pattern>>() {});

                // Manually set the parent references for Hibernate to persist foreign keys correctly
                for (Pattern pattern : patterns) {
                    if (pattern.getSubPatterns() != null) {
                        for (SubPattern subPattern : pattern.getSubPatterns()) {
                            subPattern.setPattern(pattern);
                            if (subPattern.getQuestions() != null) {
                                for (Question question : subPattern.getQuestions()) {
                                    question.setSubPattern(subPattern);
                                }
                            }
                        }
                    }
                }

                patternRepository.saveAll(patterns);
                System.out.println("✅ Successfully seeded DSA Patterns into PostgreSQL!");

            } catch (Exception e) {
                System.err.println("❌ Failed to seed data: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("⚡ Data already exists. Skipping initialization.");
        }
    }
}