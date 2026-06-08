package com.stanionutraul.service;

import com.stanionutraul.dto.WorkoutRequestDTO;
import com.stanionutraul.dto.WorkoutResponseDTO;
import com.stanionutraul.mapper.WorkoutMapper;
import com.stanionutraul.model.Role;
import com.stanionutraul.model.User;
import com.stanionutraul.model.Workout;
import com.stanionutraul.repository.UserRepository;
import com.stanionutraul.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public List<WorkoutResponseDTO> getAllWorkouts() {
        return workoutRepository.findAll()
                .stream()
                .map(WorkoutMapper::toDTO)
                .collect(Collectors.toList());
    }

    public WorkoutResponseDTO insertWorkout(WorkoutRequestDTO dto) {

        Workout workout = WorkoutMapper.toEntity(dto);

        if (dto.getTrainerId() != null) {

            User trainer = userRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (trainer.getRole() != Role.TRAINER) {
                throw new RuntimeException("Selected user is not a trainer");
            }

            workout.setTrainer(trainer);
        }

        Workout saved = workoutRepository.save(workout);

        return WorkoutMapper.toDTO(saved);
    }

    public void deleteWorkout(Integer id) {
        if (!workoutRepository.existsById(id)) {
            throw new RuntimeException("Workout not found");
        }
        workoutRepository.deleteById(id);
    }

    public WorkoutResponseDTO updateWorkout(Integer id, WorkoutRequestDTO dto) {

        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        workout.setName(dto.getName());
        workout.setDescription(dto.getDescription());
        workout.setDurationMinutes(dto.getDurationMinutes());
        workout.setDifficulty(dto.getDifficulty());
        workout.setCategory(dto.getCategory());

        if (dto.getTrainerId() != null) {

            User trainer = userRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (trainer.getRole() != Role.TRAINER) {
                throw new RuntimeException("Selected user is not a trainer");
            }

            workout.setTrainer(trainer);
        }

        Workout updated = workoutRepository.save(workout);

        return WorkoutMapper.toDTO(updated);
    }

    public WorkoutResponseDTO getWorkoutById(Integer id) {

        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        return WorkoutMapper.toDTO(workout);
    }
}