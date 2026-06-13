package com.stanionutraul.service;

import com.stanionutraul.dto.ChangePasswordRequest;
import com.stanionutraul.dto.UpdateProfileRequest;
import com.stanionutraul.dto.UserProfileDTO;
import com.stanionutraul.model.Role;
import com.stanionutraul.model.User;
import com.stanionutraul.repository.EmailVerificationTokenRepository;
import com.stanionutraul.repository.UserRepository;
import com.stanionutraul.repository.UserWorkoutRepository;
import com.stanionutraul.repository.WorkoutRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserWorkoutRepository userWorkoutRepository;
    private final EmailVerificationTokenRepository tokenRepository;
    private final WorkoutRepository workoutRepository;

    public UserProfileService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserWorkoutRepository userWorkoutRepository,
            EmailVerificationTokenRepository tokenRepository,
            WorkoutRepository workoutRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userWorkoutRepository = userWorkoutRepository;
        this.tokenRepository = tokenRepository;
        this.workoutRepository = workoutRepository;
    }

    public UserProfileDTO getProfile(User user) {
        return toDto(user);
    }

    public UserProfileDTO updateProfile(User user, UpdateProfileRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        user.setName(request.getName().trim());

        User saved = userRepository.save(user);

        return toDto(saved);
    }

    public void changePassword(User user, ChangePasswordRequest request) {
        if (request.getCurrentPassword() == null || request.getCurrentPassword().isBlank()) {
            throw new RuntimeException("Current password is required");
        }

        if (request.getNewPassword() == null || request.getNewPassword().length() < 4) {
            throw new RuntimeException("New password must be at least 4 characters");
        }

        boolean matches = passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    private UserProfileDTO toDto(User user) {
        UserProfileDTO dto = new UserProfileDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());

        if (user.getMembership() != null) {
            dto.setMembership(user.getMembership().getType());
        } else {
            dto.setMembership(null);
        }

        return dto;
    }


    @Transactional
    public void resetProgress(User user) {
        userWorkoutRepository.deleteByUserId(user.getId());
    }

    @Transactional
    public void deleteAccount(User user) {
        if (user.getRole() == Role.TRAINER) {
            boolean hasActiveWorkouts = !workoutRepository
                    .findByTrainerIdAndArchivedFalse(user.getId())
                    .isEmpty();

            if (hasActiveWorkouts) {
                throw new RuntimeException("Trainers with active workouts cannot delete their account yet");
            }
        }

        userWorkoutRepository.deleteByUserId(user.getId());
        tokenRepository.deleteByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }
}