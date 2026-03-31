package com.stanionutraul.mapper;

import com.stanionutraul.dto.WorkoutRequestDTO;
import com.stanionutraul.dto.WorkoutResponseDTO;
import com.stanionutraul.model.Workout;

public class WorkoutMapper {
    public static Workout toEntity(WorkoutRequestDTO dto) {
        Workout workout = new Workout();
        workout.setName(dto.getName());
        workout.setDescription(dto.getDescription());
        return workout;
    }

    public static WorkoutResponseDTO toDTO(Workout workout) {
        WorkoutResponseDTO dto = new WorkoutResponseDTO();
        dto.setId(workout.getId());
        dto.setName(workout.getName());
        dto.setDescription(workout.getDescription());

        if(workout.getTrainer() != null){
            dto.setTrainerName(workout.getTrainer().getName());
        }
        return dto;
    }



}
