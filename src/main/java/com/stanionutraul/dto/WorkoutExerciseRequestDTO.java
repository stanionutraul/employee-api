package com.stanionutraul.dto;

public class WorkoutExerciseRequestDTO {

    private String name;
    private Integer sets;
    private String reps;
    private String notes;
    private Integer position;

    public String getName() {
        return name;
    }

    public Integer getSets() {
        return sets;
    }

    public String getReps() {
        return reps;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getPosition() {
        return position;
    }
}