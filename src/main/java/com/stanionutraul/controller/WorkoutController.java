package com.stanionutraul.controller;


import com.stanionutraul.dto.WorkoutRequestDTO;
import com.stanionutraul.dto.WorkoutResponseDTO;
import com.stanionutraul.service.WorkoutService;
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
    public List<WorkoutResponseDTO> getAllWorkouts(){
        return workoutService.getAllWorkouts();
    }

    @GetMapping("{id}")
    public WorkoutResponseDTO getWorkoutById(@PathVariable int id){
        return workoutService.getWorkoutById(id);
    }

    @PostMapping
    public WorkoutResponseDTO addWorkout(@RequestBody WorkoutRequestDTO workoutRequestDTO){
        return workoutService.insertWorkout(workoutRequestDTO);
    }

    @PutMapping("{id}")
    public WorkoutResponseDTO updateWorkout(@PathVariable Integer id, @RequestBody WorkoutRequestDTO workoutRequestDTO){
        return workoutService.updateWorkout(id, workoutRequestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteWorkout(@PathVariable Integer id){
        workoutService.deleteWorkout(id);
    }


}
