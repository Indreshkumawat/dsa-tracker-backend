package com.example.DSATracker.controller;

import com.example.DSATracker.dto.PatternDto;
import com.example.DSATracker.service.PatternService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/patterns")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PatternController {

    private final PatternService patternService;

    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }

    @GetMapping
    public ResponseEntity<List<PatternDto>> getFullDsaRoadmap() {
        List<PatternDto> roadmap = patternService.getAllPatterns();
        return ResponseEntity.ok(roadmap);
    }
}