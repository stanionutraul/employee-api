package com.stanionutraul.controller;

import com.stanionutraul.dto.ChangePasswordRequest;
import com.stanionutraul.dto.UpdateProfileRequest;
import com.stanionutraul.dto.UserProfileDTO;
import com.stanionutraul.model.User;
import com.stanionutraul.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<UserProfileDTO> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(userProfileService.getProfile(user));
    }

    @PutMapping
    public ResponseEntity<UserProfileDTO> updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request
    ) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(userProfileService.updateProfile(user, request));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            Authentication authentication,
            @RequestBody ChangePasswordRequest request
    ) {
        User user = (User) authentication.getPrincipal();

        userProfileService.changePassword(user, request);

        return ResponseEntity.noContent().build();
    }
}