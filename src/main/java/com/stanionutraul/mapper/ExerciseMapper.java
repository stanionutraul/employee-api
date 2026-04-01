package com.stanionutraul.mapper;

import com.stanionutraul.dto.ExerciseRequestDTO;
import com.stanionutraul.dto.ExerciseResponseDTO;
import com.stanionutraul.model.Exercise;

public class ExerciseMapper {

    public static Exercise toEntity(ExerciseRequestDTO dto){
        Exercise exercise = new Exercise();
        exercise.setName(dto.getName());
        exercise.setSets(dto.getSets());
        exercise.setReps(dto.getReps());
        return exercise;
    }


    public static ExerciseResponseDTO toDTO(Exercise exercise){
        ExerciseResponseDTO dto = new ExerciseResponseDTO();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setSets(exercise.getSets());
        dto.setReps(exercise.getReps());
        if (exercise.getWorkout() != null && exercise.getWorkout().getName() != null) {
            dto.setWorkoutName(exercise.getWorkout().getName());
        }

        return dto;

    }

}
