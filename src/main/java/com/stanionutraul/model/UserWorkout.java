package com.stanionutraul.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class UserWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Workout workout;

    private String date;

    @Column(nullable = false)
    private boolean completed = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserWorkoutStatus status = UserWorkoutStatus.SCHEDULED;

    public UserWorkout() {}

    public UserWorkoutStatus getStatus() {
        return status;
    }

    public void setStatus(UserWorkoutStatus status) {
        this.status = status;
    }

    public UserWorkout(Integer id, User user, Workout workout, String date) {
        this.id = id;
        this.user = user;
        this.workout = workout;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserWorkout that = (UserWorkout) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(workout, that.workout) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, workout, date);
    }
}
