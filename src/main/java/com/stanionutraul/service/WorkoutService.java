package com.stanionutraul.service;
import com.stanionutraul.dto.WorkoutRequestDTO;
import com.stanionutraul.dto.WorkoutResponseDTO;
import com.stanionutraul.mapper.WorkoutMapper;
import com.stanionutraul.model.Trainer;
import com.stanionutraul.model.Workout;
import com.stanionutraul.repository.TrainerRepository;
import com.stanionutraul.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final TrainerRepository trainerRepository;

    public WorkoutService(WorkoutRepository workoutRepository, TrainerRepository trainerRepository) {
        this.workoutRepository = workoutRepository;
        this.trainerRepository = trainerRepository;
    }

    public List<WorkoutResponseDTO> getAllWorkouts() {
        return workoutRepository.findAll().stream().map(workout -> WorkoutMapper.toDTO(workout)).collect(Collectors.toList());
    }

    public WorkoutResponseDTO insertWorkout(WorkoutRequestDTO dto) {
        Workout workout = WorkoutMapper.toEntity(dto);
        if(dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId()).orElseThrow(() -> new RuntimeException("Trainer not found"));

            workout.setTrainer(trainer);
        }

        Workout saved = workoutRepository.save(workout);

        return WorkoutMapper.toDTO(saved);
    }

    public void deleteWorkout(Integer id) {
        if(!workoutRepository.existsById(id)) {
            throw new RuntimeException("Workout not found");
        }
        workoutRepository.deleteById(id);
    }

    public WorkoutResponseDTO updateWorkout(Integer id, WorkoutRequestDTO dto) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));

        workout.setName(dto.getName());
        workout.setDescription(dto.getDescription());

        if(dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId()).orElseThrow(() -> new RuntimeException("Trainer not found"));

            workout.setTrainer(trainer);
        }

        Workout updated = workoutRepository.save(workout);

        return WorkoutMapper.toDTO(updated);
    }

    public WorkoutResponseDTO getWorkoutById(Integer id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        return WorkoutMapper.toDTO(workout);
    }

}