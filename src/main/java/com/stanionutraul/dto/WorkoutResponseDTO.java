package com.stanionutraul.dto;

import com.stanionutraul.model.WorkoutCategory;
import com.stanionutraul.model.WorkoutDifficulty;

public class WorkoutResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private WorkoutDifficulty difficulty;
    private WorkoutCategory category;
    private String trainerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public WorkoutDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(WorkoutDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public WorkoutCategory getCategory() {
        return category;
    }

    public void setCategory(WorkoutCategory category) {
        this.category = category;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
}