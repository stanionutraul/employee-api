package com.stanionutraul.config;

import com.stanionutraul.model.Role;
import com.stanionutraul.model.User;
import com.stanionutraul.model.Workout;
import com.stanionutraul.model.WorkoutCategory;
import com.stanionutraul.model.WorkoutDifficulty;
import com.stanionutraul.model.WorkoutExercise;
import com.stanionutraul.repository.UserRepository;
import com.stanionutraul.repository.WorkoutExerciseRepository;
import com.stanionutraul.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User alex = createTrainerIfMissing(
                "Alex Carter",
                "alex.trainer@nexusfit.com"
        );

        User maria = createTrainerIfMissing(
                "Maria Stone",
                "maria.trainer@nexusfit.com"
        );

        createWorkoutIfMissing(
                "Push Day",
                "Perfect upper body push workout focused on chest, shoulders and triceps.",
                45,
                WorkoutDifficulty.INTERMEDIATE,
                WorkoutCategory.HYPERTROPHY,
                alex,
                new ExerciseSeed[]{
                        new ExerciseSeed("Bench Press", 4, "6-8", "Progressive overload. Keep shoulder blades tight and control the negative."),
                        new ExerciseSeed("Incline Dumbbell Press", 3, "8-10", "Focus on upper chest. Do not bounce the weights."),
                        new ExerciseSeed("Cable Fly", 3, "10-12", "Slow stretch and strong squeeze at the top."),
                        new ExerciseSeed("Lateral Raises", 3, "12-15", "Light weight, strict form, no swinging."),
                        new ExerciseSeed("Triceps Pushdown", 3, "10-12", "Keep elbows fixed and fully extend at the bottom.")
                }
        );

        createWorkoutIfMissing(
                "Pull Day",
                "A strong pull session for back thickness, lats and biceps.",
                50,
                WorkoutDifficulty.INTERMEDIATE,
                WorkoutCategory.STRENGTH,
                alex,
                new ExerciseSeed[]{
                        new ExerciseSeed("Pull Ups", 4, "6-10", "Use assistance if needed. Full range of motion."),
                        new ExerciseSeed("Barbell Row", 4, "6-8", "Keep back neutral and pull toward lower ribs."),
                        new ExerciseSeed("Lat Pulldown", 3, "8-10", "Drive elbows down and avoid leaning too far back."),
                        new ExerciseSeed("Face Pull", 3, "12-15", "Great for rear delts and shoulder health."),
                        new ExerciseSeed("Dumbbell Curl", 3, "10-12", "Controlled reps. Avoid swinging.")
                }
        );

        createWorkoutIfMissing(
                "Leg Day",
                "Complete lower body workout targeting quads, hamstrings, glutes and calves.",
                60,
                WorkoutDifficulty.ADVANCED,
                WorkoutCategory.STRENGTH,
                alex,
                new ExerciseSeed[]{
                        new ExerciseSeed("Back Squat", 4, "5-8", "Brace hard, maintain depth and increase load progressively."),
                        new ExerciseSeed("Romanian Deadlift", 4, "8-10", "Hinge from the hips and keep tension in hamstrings."),
                        new ExerciseSeed("Leg Press", 3, "10-12", "Controlled tempo. Do not lock knees aggressively."),
                        new ExerciseSeed("Walking Lunges", 3, "10 each leg", "Keep torso stable and step with control."),
                        new ExerciseSeed("Standing Calf Raise", 4, "12-15", "Pause at the top and stretch at the bottom.")
                }
        );

        createWorkoutIfMissing(
                "Full Body Beginner",
                "Simple and efficient full body routine for building consistency.",
                35,
                WorkoutDifficulty.BEGINNER,
                WorkoutCategory.STRENGTH,
                maria,
                new ExerciseSeed[]{
                        new ExerciseSeed("Goblet Squat", 3, "10-12", "Keep chest up and move slowly."),
                        new ExerciseSeed("Push Ups", 3, "8-12", "Use knees if needed. Keep body straight."),
                        new ExerciseSeed("Seated Cable Row", 3, "10-12", "Squeeze shoulder blades together."),
                        new ExerciseSeed("Dumbbell Romanian Deadlift", 3, "10-12", "Focus on hip hinge and hamstring stretch."),
                        new ExerciseSeed("Plank", 3, "30-45 sec", "Brace core and avoid sagging hips.")
                }
        );

        createWorkoutIfMissing(
                "HIIT Cardio",
                "Short high-intensity conditioning workout to improve endurance and burn calories.",
                25,
                WorkoutDifficulty.INTERMEDIATE,
                WorkoutCategory.CARDIO,
                maria,
                new ExerciseSeed[]{
                        new ExerciseSeed("Jumping Jacks", 4, "40 sec", "Move fast but stay controlled."),
                        new ExerciseSeed("Mountain Climbers", 4, "30 sec", "Keep core tight and drive knees forward."),
                        new ExerciseSeed("Burpees", 4, "8-12", "Scale by removing the jump if needed."),
                        new ExerciseSeed("High Knees", 4, "30 sec", "Stay light on your feet."),
                        new ExerciseSeed("Rest Walk", 4, "60 sec", "Recover before the next round.")
                }
        );

        createWorkoutIfMissing(
                "Core Crusher",
                "Focused core workout for abs, stability and trunk control.",
                30,
                WorkoutDifficulty.BEGINNER,
                WorkoutCategory.CORE,
                maria,
                new ExerciseSeed[]{
                        new ExerciseSeed("Dead Bug", 3, "10 each side", "Keep lower back pressed into the floor."),
                        new ExerciseSeed("Cable Crunch", 3, "12-15", "Round the spine and squeeze abs hard."),
                        new ExerciseSeed("Hanging Knee Raise", 3, "8-12", "Control the movement and avoid swinging."),
                        new ExerciseSeed("Side Plank", 3, "30 sec each side", "Keep hips high and body aligned."),
                        new ExerciseSeed("Russian Twist", 3, "16-20", "Rotate with control, not momentum.")
                }
        );

        createWorkoutIfMissing(
                "Mobility Reset",
                "Low intensity mobility session for recovery, posture and better movement quality.",
                30,
                WorkoutDifficulty.BEGINNER,
                WorkoutCategory.MOBILITY,
                maria,
                new ExerciseSeed[]{
                        new ExerciseSeed("Cat Cow", 2, "10-12", "Move slowly through the spine."),
                        new ExerciseSeed("World's Greatest Stretch", 2, "6 each side", "Open hips and rotate through upper back."),
                        new ExerciseSeed("Hip Flexor Stretch", 2, "45 sec each side", "Squeeze glute to deepen the stretch."),
                        new ExerciseSeed("Thoracic Rotation", 2, "8 each side", "Follow your hand with your eyes."),
                        new ExerciseSeed("Child's Pose Breathing", 2, "60 sec", "Slow breathing and full relaxation.")
                }
        );
    }

    private User createTrainerIfMissing(String name, String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User user = new User();

                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode("Password123"));
                    user.setRole(Role.TRAINER);
                    user.setEmailVerified(true);

                    return userRepository.save(user);
                });
    }

    private void createWorkoutIfMissing(
            String name,
            String description,
            Integer durationMinutes,
            WorkoutDifficulty difficulty,
            WorkoutCategory category,
            User trainer,
            ExerciseSeed[] exercises
    ) {
        boolean exists = workoutRepository.findByArchivedFalse()
                .stream()
                .anyMatch(workout ->
                        workout.getName().equalsIgnoreCase(name)
                                && workout.getTrainer() != null
                                && workout.getTrainer().getEmail().equalsIgnoreCase(trainer.getEmail())
                );

        if (exists) {
            return;
        }

        Workout workout = new Workout();
        workout.setName(name);
        workout.setDescription(description);
        workout.setDurationMinutes(durationMinutes);
        workout.setDifficulty(difficulty);
        workout.setCategory(category);
        workout.setTrainer(trainer);
        workout.setArchived(false);

        Workout savedWorkout = workoutRepository.save(workout);

        for (int i = 0; i < exercises.length; i++) {
            ExerciseSeed seed = exercises[i];

            WorkoutExercise exercise = new WorkoutExercise();
            exercise.setWorkout(savedWorkout);
            exercise.setName(seed.name());
            exercise.setSets(seed.sets());
            exercise.setReps(seed.reps());
            exercise.setNotes(seed.notes());
            exercise.setPosition(i + 1);

            workoutExerciseRepository.save(exercise);
        }
    }

    private record ExerciseSeed(
            String name,
            Integer sets,
            String reps,
            String notes
    ) {}
}