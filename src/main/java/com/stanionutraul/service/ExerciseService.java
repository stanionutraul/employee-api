package com.stanionutraul.service;


import com.stanionutraul.dto.ExerciseRequestDTO;
import com.stanionutraul.dto.ExerciseResponseDTO;
import com.stanionutraul.mapper.ExerciseMapper;
import com.stanionutraul.model.Exercise;
import com.stanionutraul.model.Workout;
import com.stanionutraul.repository.ExerciseRepository;
import com.stanionutraul.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    public final ExerciseRepository exerciseRepository;
    public final WorkoutRepository workoutRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
    }

    public List<ExerciseResponseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream().map(exercise -> ExerciseMapper.toDTO(exercise)).collect(Collectors.toList()).stream().collect(Collectors.toList());
    }

    public ExerciseResponseDTO insertExercise(ExerciseRequestDTO dto) {

        if (dto.getWorkoutId() == null) {
            throw new RuntimeException("WorkoutId must not be null");
        }

        Exercise exercise = ExerciseMapper.toEntity(dto);

        Workout workout = workoutRepository.findById(dto.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        exercise.setWorkout(workout);

        Exercise savedExercise = exerciseRepository.save(exercise);

        return ExerciseMapper.toDTO(savedExercise);
    }

    public void deleteExercise(Integer id) {
        if (!exerciseRepository.existsById(id)) {
            throw new RuntimeException("Exercise not found");
        }
        exerciseRepository.deleteById(id);
    }

    public ExerciseResponseDTO updateExercise(Integer id,ExerciseRequestDTO dto) {
        Exercise existingExercise = exerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercise not found"));

        existingExercise.setName(dto.getName());
        existingExercise.setSets(dto.getSets());
        existingExercise.setReps(dto.getReps());

        if(dto.getWorkoutId() != null) {
            Workout workout = workoutRepository.findById(dto.getWorkoutId()).orElseThrow(() -> new RuntimeException("Workout not found"));
            existingExercise.setWorkout(workout);
        }
        Exercise updatedExercise = exerciseRepository.save(existingExercise);
        return ExerciseMapper.toDTO(updatedExercise);
    }

    public ExerciseResponseDTO getExerciseById(Integer id) {
            Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercise not found"));
            return ExerciseMapper.toDTO(exercise);

    }
}
