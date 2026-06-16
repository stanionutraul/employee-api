package com.stanionutraul.controller;

import com.stanionutraul.dto.UserWorkoutRequestDTO;
import com.stanionutraul.dto.UserWorkoutResponseDTO;
import com.stanionutraul.service.UserWorkoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-workouts")
public class UserWorkoutController {

    private final UserWorkoutService userWorkoutService;

    public UserWorkoutController(UserWorkoutService userWorkoutService) {
        this.userWorkoutService = userWorkoutService;
    }

    // CREATE
    @PostMapping
    public UserWorkoutResponseDTO create(@RequestBody UserWorkoutRequestDTO dto) {
        return userWorkoutService.insertUserWorkout(dto);
    }

    // GET ALL
    @GetMapping
    public List<UserWorkoutResponseDTO> getAll() {
        return userWorkoutService.getAllUserWorkouts();
    }

    // GET BY ID
    @GetMapping("{id}")
    public UserWorkoutResponseDTO getById(@PathVariable Integer id) {
        return userWorkoutService.getUserWorkoutById(id);
    }

    // GET BY USER
    @GetMapping("user/{userId}")
    public List<UserWorkoutResponseDTO> getByUser(@PathVariable Integer userId) {
        return userWorkoutService.getUserById(userId);
    }

    // DELETE
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        userWorkoutService.delete(id);
    }


    //COMPLETE WORKOUT
    @PutMapping("{id}/complete")
    public UserWorkoutResponseDTO complete(@PathVariable Integer id) {
        return userWorkoutService.complete(id);
    }


    @GetMapping("user/{userId}/pending-review")
    public List<UserWorkoutResponseDTO> getPendingReview(@PathVariable Integer userId) {
        return userWorkoutService.getPendingReview(userId);
    }

    @PutMapping("{id}/missed")
    public UserWorkoutResponseDTO markMissed(@PathVariable Integer id) {
        return userWorkoutService.markMissed(id);
    }
}