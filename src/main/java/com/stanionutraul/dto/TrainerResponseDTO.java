package com.stanionutraul.dto;

import java.util.List;

public class TrainerResponseDTO {

    private Integer id;
    private String name;
    private List<String> workoutsNames;

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

    public List<String> getWorkoutsNames() {
        return workoutsNames;
    }

    public void setWorkoutsNames(List<String> workoutsNames) {
        this.workoutsNames = workoutsNames;
    }
}
