package com.stanionutraul.mapper;

import com.stanionutraul.dto.UserWorkoutRequestDTO;
import com.stanionutraul.dto.UserWorkoutResponseDTO;
import com.stanionutraul.model.User;
import com.stanionutraul.model.UserWorkout;

public class UserWorkoutMapper {

    public static UserWorkout toEntity(UserWorkoutRequestDTO dto){
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setDate(dto.getDate());

        return userWorkout;
    }

    public static UserWorkoutResponseDTO toDto(UserWorkout userWorkout){
        UserWorkoutResponseDTO dto = new UserWorkoutResponseDTO();
        dto.setId(userWorkout.getId());
        dto.setDate(userWorkout.getDate());
        dto.setCompleted(userWorkout.isCompleted());

        if(userWorkout.getUser() != null){
            dto.setUserName(userWorkout.getUser().getName());
        }

        if(userWorkout.getWorkout() != null){
            dto.setWorkoutName(userWorkout.getWorkout().getName());
        }

        return dto;
    }
}
