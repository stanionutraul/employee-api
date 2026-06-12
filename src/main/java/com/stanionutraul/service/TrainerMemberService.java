package com.stanionutraul.service;

import com.stanionutraul.dto.TrainerMemberDTO;
import com.stanionutraul.model.Role;
import com.stanionutraul.model.User;
import com.stanionutraul.model.UserWorkout;
import com.stanionutraul.repository.UserWorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrainerMemberService {

    private final UserWorkoutRepository userWorkoutRepository;

    public TrainerMemberService(UserWorkoutRepository userWorkoutRepository) {
        this.userWorkoutRepository = userWorkoutRepository;
    }

    public List<TrainerMemberDTO> getMembersForTrainer(User trainer) {
        if (trainer.getRole() != Role.TRAINER && trainer.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only trainers can view members");
        }

        List<UserWorkout> sessions = userWorkoutRepository
                .findByWorkoutTrainerId(trainer.getId());

        String mostPopularWorkout = getMostPopularWorkout(sessions);

        Map<User, List<UserWorkout>> groupedByUser = sessions.stream()
                .filter(session -> session.getUser() != null)
                .filter(session -> !session.getUser().getId().equals(trainer.getId()))
                .collect(Collectors.groupingBy(UserWorkout::getUser));

        LocalDateTime now = LocalDateTime.now();

        return groupedByUser.entrySet()
                .stream()
                .map(entry -> {
                    User user = entry.getKey();
                    List<UserWorkout> userSessions = entry.getValue();

                    TrainerMemberDTO dto = new TrainerMemberDTO();

                    dto.setUserId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());

                    dto.setTotalSessions(userSessions.size());

                    int completed = (int) userSessions.stream()
                            .filter(UserWorkout::isCompleted)
                            .count();

                    int upcoming = (int) userSessions.stream()
                            .filter(session -> !session.isCompleted())
                            .filter(session -> isFuture(session.getDate(), now))
                            .count();

                    dto.setCompletedSessions(completed);
                    dto.setUpcomingSessions(upcoming);

                    String lastActivity = userSessions.stream()
                            .map(UserWorkout::getDate)
                            .filter(date -> date != null && !date.isBlank())
                            .max(String::compareTo)
                            .orElse(null);

                    dto.setLastActivity(lastActivity);

                    List<String> workoutNames = userSessions.stream()
                            .filter(session -> session.getWorkout() != null)
                            .map(session -> session.getWorkout().getName())
                            .distinct()
                            .toList();

                    dto.setWorkoutNames(workoutNames);
                    dto.setMostPopularWorkout(mostPopularWorkout);

                    return dto;
                })
                .sorted(Comparator.comparing(
                        TrainerMemberDTO::getLastActivity,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .toList();
    }

    private String getMostPopularWorkout(List<UserWorkout> sessions) {
        return sessions.stream()
                .filter(session -> session.getWorkout() != null)
                .collect(Collectors.groupingBy(
                        session -> session.getWorkout().getName(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("—");
    }

    private boolean isFuture(String value, LocalDateTime now) {
        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            return LocalDateTime.parse(value).isAfter(now);
        } catch (Exception e) {
            return false;
        }
    }
}