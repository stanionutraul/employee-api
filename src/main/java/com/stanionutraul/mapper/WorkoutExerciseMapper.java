package com.stanionutraul.mapper;

import com.stanionutraul.dto.WorkoutExerciseRequestDTO;
import com.stanionutraul.dto.WorkoutExerciseResponseDTO;
import com.stanionutraul.model.WorkoutExercise;

public class WorkoutExerciseMapper {

    public static WorkoutExercise toEntity(WorkoutExerciseRequestDTO dto) {
        WorkoutExercise exercise = new WorkoutExercise();

        exercise.setName(dto.getName());
        exercise.setSets(dto.getSets());
        exercise.setReps(dto.getReps());
        exercise.setNotes(dto.getNotes());
        exercise.setPosition(dto.getPosition());

        return exercise;
    }

    public static WorkoutExerciseResponseDTO toDto(WorkoutExercise exercise) {
        WorkoutExerciseResponseDTO dto = new WorkoutExerciseResponseDTO();

        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setSets(exercise.getSets());
        dto.setReps(exercise.getReps());
        dto.setNotes(exercise.getNotes());
        dto.setPosition(exercise.getPosition());

        if (exercise.getWorkout() != null) {
            dto.setWorkoutId(exercise.getWorkout().getId());
        }

        return dto;
    }
}