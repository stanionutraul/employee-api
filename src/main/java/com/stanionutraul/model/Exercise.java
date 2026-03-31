package com.stanionutraul.model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer sets;
    private Integer reps;


    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

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

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(id, exercise.id) && Objects.equals(name, exercise.name) && Objects.equals(sets, exercise.sets) && Objects.equals(reps, exercise.reps) && Objects.equals(workout, exercise.workout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sets, reps, workout);
    }
}
