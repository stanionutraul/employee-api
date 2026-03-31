package com.stanionutraul.service;


import com.stanionutraul.dto.TrainerRequestDTO;
import com.stanionutraul.dto.TrainerResponseDTO;
import com.stanionutraul.mapper.TrainerMapper;
import com.stanionutraul.model.Trainer;
import com.stanionutraul.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;


    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<TrainerResponseDTO> getAllTrainers() {
        return trainerRepository.findAll().stream().map(trainer -> TrainerMapper.toDto(trainer)).collect(Collectors.toList());
    }

    public TrainerResponseDTO insertTrainer(TrainerRequestDTO dto) {
        Trainer trainer = TrainerMapper.toEntity(dto);
        Trainer saved = trainerRepository.save(trainer);

        return TrainerMapper.toDto(saved);
    }

    public TrainerResponseDTO getTrainerById(Integer id) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trainer not found"));
        return TrainerMapper.toDto(trainer);
    }

    public void deleteTrainer(Integer id) {
        if(trainerRepository.existsById(id)) {
            throw new RuntimeException("Trainer not found");
        }
        trainerRepository.deleteById(id);
    }




}
