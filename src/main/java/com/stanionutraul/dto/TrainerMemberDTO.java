package com.stanionutraul.dto;

import java.util.List;

public class TrainerMemberDTO {

    private Integer userId;
    private String name;
    private String email;

    private Integer totalSessions;
    private Integer completedSessions;
    private Integer upcomingSessions;

    private String lastActivity;

    private List<String> workoutNames;

    private String mostPopularWorkout;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(Integer totalSessions) {
        this.totalSessions = totalSessions;
    }

    public Integer getCompletedSessions() {
        return completedSessions;
    }

    public void setCompletedSessions(Integer completedSessions) {
        this.completedSessions = completedSessions;
    }

    public Integer getUpcomingSessions() {
        return upcomingSessions;
    }

    public void setUpcomingSessions(Integer upcomingSessions) {
        this.upcomingSessions = upcomingSessions;
    }

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    public List<String> getWorkoutNames() {
        return workoutNames;
    }

    public void setWorkoutNames(List<String> workoutNames) {
        this.workoutNames = workoutNames;
    }

    public String getMostPopularWorkout() {
        return mostPopularWorkout;
    }

    public void setMostPopularWorkout(String mostPopularWorkout) {
        this.mostPopularWorkout = mostPopularWorkout;
    }
}