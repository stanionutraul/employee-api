package com.stanionutraul.repository;

import com.stanionutraul.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout,Integer> {}
