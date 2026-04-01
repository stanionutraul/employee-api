package com.stanionutraul.controller;


import com.stanionutraul.dto.ExerciseRequestDTO;
import com.stanionutraul.dto.ExerciseResponseDTO;
import com.stanionutraul.model.Exercise;
import com.stanionutraul.service.ExerciseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ExerciseResponseDTO addExercise(@RequestBody ExerciseRequestDTO dto) {
        return exerciseService.insertExercise(dto);
    }

    @GetMapping
    public List<ExerciseResponseDTO> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("{id}")
    public ExerciseResponseDTO getExerciseById(@PathVariable Integer id) {
        return exerciseService.getExerciseById(id);
    }

    @PutMapping("{id}")
    public ExerciseResponseDTO updateExercise(@PathVariable Integer id, @RequestBody ExerciseRequestDTO dto) {
        return exerciseService.updateExercise(id, dto);
    }

    @DeleteMapping("{id}")
    public void deleteExercise(@PathVariable Integer id) {
        exerciseService.deleteExercise(id);
    }
}
