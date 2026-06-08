package com.stanionutraul.repository;

import com.stanionutraul.model.UserWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWorkoutRepository extends JpaRepository<UserWorkout, Integer> {

    List<UserWorkout> findByUserId(Integer userId);

}