package com.stanionutraul.mapper;

import com.stanionutraul.dto.WorkoutRequestDTO;
import com.stanionutraul.dto.WorkoutResponseDTO;
import com.stanionutraul.model.Workout;

public class WorkoutMapper {

    public static Workout toEntity(WorkoutRequestDTO dto) {
        Workout workout = new Workout();

        workout.setName(dto.getName());
        workout.setDescription(dto.getDescription());
        workout.setDurationMinutes(dto.getDurationMinutes());
        workout.setDifficulty(dto.getDifficulty());
        workout.setCategory(dto.getCategory());

        return workout;
    }

    public static WorkoutResponseDTO toDTO(Workout workout) {
        WorkoutResponseDTO dto = new WorkoutResponseDTO();

        dto.setId(workout.getId());
        dto.setName(workout.getName());
        dto.setDescription(workout.getDescription());
        dto.setDurationMinutes(workout.getDurationMinutes());
        dto.setDifficulty(workout.getDifficulty());
        dto.setCategory(workout.getCategory());

        if (workout.getTrainer() != null) {
            dto.setTrainerId(workout.getTrainer().getId());
            dto.setTrainerName(workout.getTrainer().getName());
        }

        return dto;
    }
}