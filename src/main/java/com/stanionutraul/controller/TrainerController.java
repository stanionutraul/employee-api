package com.stanionutraul.controller;

import com.stanionutraul.dto.TrainerRequestDTO;
import com.stanionutraul.dto.TrainerResponseDTO;
import com.stanionutraul.service.TrainerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public TrainerResponseDTO addTrainer(@RequestBody TrainerRequestDTO dto) {
        return trainerService.insertTrainer(dto);
    }

    @GetMapping
    public List<TrainerResponseDTO> getAll() {
        return trainerService.getAllTrainers();
    }

    @GetMapping("/{id}")
    public TrainerResponseDTO getById(@PathVariable Integer id) {
        return trainerService.getTrainerById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        trainerService.deleteTrainer(id);
    }
}