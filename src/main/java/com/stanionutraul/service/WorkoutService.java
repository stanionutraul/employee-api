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

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            UserRepository userRepository
    ) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    public List<WorkoutResponseDTO> getAllWorkouts() {
        return workoutRepository.findByArchivedFalse()
                .stream()
                .map(WorkoutMapper::toDTO)
                .toList();
    }

    public WorkoutResponseDTO getWorkoutById(Integer id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        return WorkoutMapper.toDTO(workout);
    }

    public WorkoutResponseDTO insertWorkout(
            WorkoutRequestDTO dto,
            User currentUser
    ) {
        Workout workout = WorkoutMapper.toEntity(dto);

        if (currentUser.getRole() == Role.TRAINER) {
            workout.setTrainer(currentUser);
        } else if (currentUser.getRole() == Role.ADMIN) {
            if (dto.getTrainerId() != null) {
                User trainer = getTrainer(dto.getTrainerId());
                workout.setTrainer(trainer);
            }
        } else {
            throw new RuntimeException("Only trainers can create workouts");
        }

        Workout saved = workoutRepository.save(workout);

        return WorkoutMapper.toDTO(saved);
    }

    public WorkoutResponseDTO updateWorkout(
            Integer id,
            WorkoutRequestDTO dto,
            User currentUser
    ) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        validateWorkoutOwner(workout, currentUser);

        workout.setName(dto.getName());
        workout.setDescription(dto.getDescription());
        workout.setDurationMinutes(dto.getDurationMinutes());
        workout.setDifficulty(dto.getDifficulty());
        workout.setCategory(dto.getCategory());

        if (currentUser.getRole() == Role.ADMIN && dto.getTrainerId() != null) {
            User trainer = getTrainer(dto.getTrainerId());
            workout.setTrainer(trainer);
        }

        Workout updated = workoutRepository.save(workout);

        return WorkoutMapper.toDTO(updated);
    }

    public void deleteWorkout(Integer id, User currentUser) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        validateWorkoutOwner(workout, currentUser);

        workout.setArchived(true);

        workoutRepository.save(workout);
    }

    private User getTrainer(Integer trainerId) {
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (trainer.getRole() != Role.TRAINER) {
            throw new RuntimeException("Selected user is not a trainer");
        }

        return trainer;
    }

    private void validateWorkoutOwner(Workout workout, User currentUser) {
        if (currentUser.getRole() == Role.ADMIN) {
            return;
        }

        if (currentUser.getRole() != Role.TRAINER) {
            throw new RuntimeException("Only trainers can manage workouts");
        }

        if (workout.getTrainer() == null ||
                !workout.getTrainer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You can only manage your own workouts");
        }
    }
}