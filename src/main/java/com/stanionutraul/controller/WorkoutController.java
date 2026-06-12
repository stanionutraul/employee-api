package com.stanionutraul.controller;

import com.stanionutraul.dto.WorkoutRequestDTO;
import com.stanionutraul.dto.WorkoutResponseDTO;
import com.stanionutraul.model.User;
import com.stanionutraul.service.WorkoutService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<WorkoutResponseDTO> getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("{id}")
    public WorkoutResponseDTO getWorkoutById(@PathVariable Integer id) {
        return workoutService.getWorkoutById(id);
    }

    @PostMapping
    public WorkoutResponseDTO addWorkout(
            @RequestBody WorkoutRequestDTO workoutRequestDTO,
            Authentication authentication
    ) {
        User currentUser = (User) authentication.getPrincipal();
        return workoutService.insertWorkout(workoutRequestDTO, currentUser);
    }

    @PutMapping("{id}")
    public WorkoutResponseDTO updateWorkout(
            @PathVariable Integer id,
            @RequestBody WorkoutRequestDTO workoutRequestDTO,
            Authentication authentication
    ) {
        User currentUser = (User) authentication.getPrincipal();
        return workoutService.updateWorkout(id, workoutRequestDTO, currentUser);
    }

    @DeleteMapping("{id}")
    public void deleteWorkout(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        User currentUser = (User) authentication.getPrincipal();
        workoutService.deleteWorkout(id, currentUser);
    }
}