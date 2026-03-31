package com.stanionutraul.mapper;

import com.stanionutraul.dto.TrainerRequestDTO;
import com.stanionutraul.dto.TrainerResponseDTO;
import com.stanionutraul.model.Trainer;

import java.util.stream.Collectors;

public class TrainerMapper {

    public static Trainer toEntity(TrainerRequestDTO dto){
        Trainer trainer = new Trainer();
        trainer.setName(dto.getName());
        return trainer;
    }


    public static TrainerResponseDTO toDto(Trainer trainer){
        TrainerResponseDTO dto = new TrainerResponseDTO();
        dto.setId(trainer.getId());
        dto.setName(trainer.getName());

        if(trainer.getWorkouts() != null){
            dto.setWorkoutsNames(trainer.getWorkouts().stream().map(w -> w.getName()).collect(Collectors.toList()));
        }
        return dto;
    }
}
