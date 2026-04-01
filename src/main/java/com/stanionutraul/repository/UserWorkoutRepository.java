package com.stanionutraul.repository;

import com.stanionutraul.model.UserWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWorkoutRepository extends JpaRepository<UserWorkout, Integer> {
}
