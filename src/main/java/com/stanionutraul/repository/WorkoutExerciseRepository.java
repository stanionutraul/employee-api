package com.stanionutraul.repository;

import com.stanionutraul.model.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {

    List<WorkoutExercise> findByWorkoutIdOrderByPositionAsc(Integer workoutId);
}