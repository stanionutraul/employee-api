package com.stanionutraul.service;

import com.stanionutraul.dto.WorkoutExerciseRequestDTO;
import com.stanionutraul.dto.WorkoutExerciseResponseDTO;
import com.stanionutraul.mapper.WorkoutExerciseMapper;
import com.stanionutraul.model.Role;
import com.stanionutraul.model.User;
import com.stanionutraul.model.Workout;
import com.stanionutraul.model.WorkoutExercise;
import com.stanionutraul.repository.WorkoutExerciseRepository;
import com.stanionutraul.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutRepository workoutRepository;

    public WorkoutExerciseService(
            WorkoutExerciseRepository workoutExerciseRepository,
            WorkoutRepository workoutRepository
    ) {
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.workoutRepository = workoutRepository;
    }

    public List<WorkoutExerciseResponseDTO> getExercisesByWorkout(Integer workoutId) {
        return workoutExerciseRepository
                .findByWorkoutIdOrderByPositionAsc(workoutId)
                .stream()
                .map(WorkoutExerciseMapper::toDto)
                .toList();
    }

    public WorkoutExerciseResponseDTO addExercise(
            Integer workoutId,
            WorkoutExerciseRequestDTO dto,
            User currentUser
    ) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Exercise name is required");
        }

        if (dto.getSets() == null || dto.getSets() <= 0) {
            throw new RuntimeException("Sets must be greater than 0");
        }

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        validateWorkoutOwner(workout, currentUser);

        WorkoutExercise exercise = WorkoutExerciseMapper.toEntity(dto);
        exercise.setWorkout(workout);

        if (exercise.getPosition() == null) {
            int nextPosition = workoutExerciseRepository
                    .findByWorkoutIdOrderByPositionAsc(workoutId)
                    .size() + 1;

            exercise.setPosition(nextPosition);
        }

        return WorkoutExerciseMapper.toDto(workoutExerciseRepository.save(exercise));
    }

    public WorkoutExerciseResponseDTO updateExercise(
            Integer id,
            WorkoutExerciseRequestDTO dto,
            User currentUser
    ) {
        WorkoutExercise exercise = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        validateWorkoutOwner(exercise.getWorkout(), currentUser);

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new RuntimeException("Exercise name is required");
        }

        if (dto.getSets() == null || dto.getSets() <= 0) {
            throw new RuntimeException("Sets must be greater than 0");
        }

        exercise.setName(dto.getName().trim());
        exercise.setSets(dto.getSets());
        exercise.setReps(dto.getReps());
        exercise.setNotes(dto.getNotes());
        exercise.setPosition(dto.getPosition());

        return WorkoutExerciseMapper.toDto(workoutExerciseRepository.save(exercise));
    }

    public void deleteExercise(Integer id, User currentUser) {
        WorkoutExercise exercise = workoutExerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        validateWorkoutOwner(exercise.getWorkout(), currentUser);

        workoutExerciseRepository.deleteById(id);
    }

    private void validateWorkoutOwner(Workout workout, User currentUser) {
        if (currentUser.getRole() == Role.ADMIN) {
            return;
        }

        if (currentUser.getRole() != Role.TRAINER) {
            throw new RuntimeException("Only trainers can manage exercises");
        }

        if (workout == null ||
                workout.getTrainer() == null ||
                !workout.getTrainer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You can only manage exercises from your own workouts");
        }
    }
}