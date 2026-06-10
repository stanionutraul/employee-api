package com.stanionutraul.controller;

import com.stanionutraul.dto.WorkoutExerciseRequestDTO;
import com.stanionutraul.dto.WorkoutExerciseResponseDTO;
import com.stanionutraul.service.WorkoutExerciseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @GetMapping("/workouts/{workoutId}/exercises")
    public List<WorkoutExerciseResponseDTO> getExercisesByWorkout(
            @PathVariable Integer workoutId
    ) {
        return workoutExerciseService.getExercisesByWorkout(workoutId);
    }

    @PostMapping("/workouts/{workoutId}/exercises")
    public WorkoutExerciseResponseDTO addExercise(
            @PathVariable Integer workoutId,
            @RequestBody WorkoutExerciseRequestDTO dto
    ) {
        return workoutExerciseService.addExercise(workoutId, dto);
    }

    @PutMapping("/workout-exercises/{id}")
    public WorkoutExerciseResponseDTO updateExercise(
            @PathVariable Integer id,
            @RequestBody WorkoutExerciseRequestDTO dto
    ) {
        return workoutExerciseService.updateExercise(id, dto);
    }

    @DeleteMapping("/workout-exercises/{id}")
    public void deleteExercise(@PathVariable Integer id) {
        workoutExerciseService.deleteExercise(id);
    }
}