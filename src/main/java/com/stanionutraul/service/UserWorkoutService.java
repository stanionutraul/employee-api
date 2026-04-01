package com.stanionutraul.service;


import com.stanionutraul.dto.UserWorkoutRequestDTO;
import com.stanionutraul.dto.UserWorkoutResponseDTO;
import com.stanionutraul.mapper.UserWorkoutMapper;
import com.stanionutraul.model.User;
import com.stanionutraul.model.UserWorkout;
import com.stanionutraul.model.Workout;
import com.stanionutraul.repository.UserRepository;
import com.stanionutraul.repository.UserWorkoutRepository;
import com.stanionutraul.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWorkoutService {
    private final UserWorkoutRepository userWorkoutRepository;
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;

    public UserWorkoutService(UserWorkoutRepository userWorkoutRepository, UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.userWorkoutRepository = userWorkoutRepository;
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }


    public List<UserWorkoutResponseDTO> getAllUserWorkouts(){
        return userWorkoutRepository.findAll().stream().map(userWorkout -> UserWorkoutMapper.toDto(userWorkout)).collect(Collectors.toList());

    }

    public UserWorkoutResponseDTO insertUserWorkout(UserWorkoutRequestDTO dto){

        if (dto.getUserId() == null || dto.getWorkoutId() == null) {
            throw new RuntimeException("userId and workoutId are required");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Workout workout = workoutRepository.findById(dto.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        UserWorkout userWorkout = UserWorkoutMapper.toEntity(dto);
        userWorkout.setUser(user);
        userWorkout.setWorkout(workout);

        return UserWorkoutMapper.toDto(userWorkoutRepository.save(userWorkout));
    }

    public UserWorkoutResponseDTO getUserWorkoutById(Integer id){
        UserWorkout userWorkout = userWorkoutRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserWorkoutMapper.toDto(userWorkout);
    }

    public List<UserWorkoutResponseDTO> getUserById(Integer userId){
        return userWorkoutRepository.findAll().stream().filter(uw -> uw.getUser() != null && uw.getUser().getId().equals(userId)).map(UserWorkoutMapper::toDto).toList();
    }


    public void delete(Integer id){
        if(!userWorkoutRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userWorkoutRepository.deleteById(id);
    }


}
