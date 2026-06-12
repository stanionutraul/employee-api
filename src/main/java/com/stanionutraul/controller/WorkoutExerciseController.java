package com.stanionutraul.controller;

import com.stanionutraul.dto.WorkoutExerciseRequestDTO;
import com.stanionutraul.dto.WorkoutExerciseResponseDTO;
import com.stanionutraul.model.User;
import com.stanionutraul.service.WorkoutExerciseService;
import org.springframework.security.core.Authentication;
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
            @RequestBody WorkoutExerciseRequestDTO dto,
            Authentication authentication
    ) {
        User currentUser = (User) authentication.getPrincipal();
        return workoutExerciseService.addExercise(workoutId, dto, currentUser);
    }

    @PutMapping("/workout-exercises/{id}")
    public WorkoutExerciseResponseDTO updateExercise(
            @PathVariable Integer id,
            @RequestBody WorkoutExerciseRequestDTO dto,
            Authentication authentication
    ) {
        User currentUser = (User) authentication.getPrincipal();
        return workoutExerciseService.updateExercise(id, dto, currentUser);
    }

    @DeleteMapping("/workout-exercises/{id}")
    public void deleteExercise(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        User currentUser = (User) authentication.getPrincipal();
        workoutExerciseService.deleteExercise(id, currentUser);
    }
}