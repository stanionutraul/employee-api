package com.stanionutraul.mapper;

import com.stanionutraul.dto.UserWorkoutRequestDTO;
import com.stanionutraul.dto.UserWorkoutResponseDTO;
import com.stanionutraul.model.UserWorkout;

public class UserWorkoutMapper {

    public static UserWorkout toEntity(UserWorkoutRequestDTO dto) {
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setDate(dto.getDate());
        return userWorkout;
    }

    public static UserWorkoutResponseDTO toDto(UserWorkout userWorkout) {
        UserWorkoutResponseDTO dto = new UserWorkoutResponseDTO();

        dto.setId(userWorkout.getId());
        dto.setDate(userWorkout.getDate());
        dto.setCompleted(userWorkout.isCompleted());
        dto.setCompleted(userWorkout.isCompleted());

        if (userWorkout.getStatus() != null) {
            dto.setStatus(userWorkout.getStatus().name());
        } else if (userWorkout.isCompleted()) {
            dto.setStatus("COMPLETED");
        } else {
            dto.setStatus("SCHEDULED");
        }

        if (userWorkout.getUser() != null) {
            dto.setUserId(userWorkout.getUser().getId());
            dto.setUserName(userWorkout.getUser().getName());
        }

        if (userWorkout.getWorkout() != null) {
            dto.setWorkoutId(userWorkout.getWorkout().getId());
            dto.setWorkoutName(userWorkout.getWorkout().getName());

            if (userWorkout.getWorkout().getTrainer() != null) {
                dto.setTrainerId(userWorkout.getWorkout().getTrainer().getId());
                dto.setTrainerName(userWorkout.getWorkout().getTrainer().getName());
            }
        }

        return dto;
    }
}