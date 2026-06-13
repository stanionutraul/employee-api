package com.stanionutraul.repository;

import com.stanionutraul.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout,Integer> {
    List<Workout> findByArchivedFalse();

    List<Workout> findByTrainerIdAndArchivedFalse(Integer trainerId);
}
