package com.stanionutraul.auth;

import com.stanionutraul.config.JwtService;
import com.stanionutraul.dto.ForgotPasswordRequest;
import com.stanionutraul.dto.ResetPasswordRequest;
import com.stanionutraul.model.EmailVerificationToken;
import com.stanionutraul.model.Role;
import com.stanionutraul.model.User;
import com.stanionutraul.repository.EmailVerificationTokenRepository;
import com.stanionutraul.repository.UserRepository;
import com.stanionutraul.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.stanionutraul.model.PasswordResetToken;
import com.stanionutraul.repository.PasswordResetTokenRepository;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    @Value("${app.backend.url}")
    private String backendUrl;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public AuthenticationResponse register(RegisterRequest request) {

        var existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (user.isEmailVerified()) {
                throw new RuntimeException("Email already exists");
            }

            resendVerificationEmail(user.getEmail());

            return AuthenticationResponse.builder()
                    .token(null)
                    .build();
        }

        Role role = request.getRole();

        if (role == null) {
            role = Role.USER;
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .emailVerified(false)
                .build();

        User savedUser = userRepository.save(user);

        String token = UUID.randomUUID().toString();

        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        verificationToken.setUsed(false);

        tokenRepository.save(verificationToken);

        String verificationUrl = frontendUrl + "/verify-email?token=" + token;

        emailService.sendVerificationEmail(
                savedUser.getEmail(),
                savedUser.getName(),
                verificationUrl
        );

        return AuthenticationResponse.builder()
                .token(null)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.isEmailVerified()) {
            throw new DisabledException("Please verify your email before logging in.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String verifyEmail(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (verificationToken.isUsed()) {
            throw new RuntimeException("Verification token already used");
        }

        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token expired");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);

        verificationToken.setUsed(true);

        userRepository.save(user);
        tokenRepository.save(verificationToken);

        return "Email verified successfully. You can now login.";
    }

    public String resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEmailVerified()) {
            return "Email is already verified.";
        }

        String token = UUID.randomUUID().toString();

        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        verificationToken.setUsed(false);

        tokenRepository.save(verificationToken);

        String verificationUrl = frontendUrl + "/verify-email?token=" + token;

        emailService.sendVerificationEmail(
                user.getEmail(),
                user.getName(),
                verificationUrl
        );

        return "Verification email sent.";
    }
    public String forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEmailVerified()) {
            throw new RuntimeException("Please verify your email first.");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        resetToken.setUsed(false);

        passwordResetTokenRepository.save(resetToken);

        String resetUrl = frontendUrl + "/reset-password?token=" + token;

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                user.getName(),
                resetUrl
        );

        return "Password reset email sent.";
    }

    public String resetPassword(ResetPasswordRequest request) {
        if (request.getNewPassword() == null || request.getNewPassword().length() < 4) {
            throw new RuntimeException("Password must be at least 4 characters");
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (resetToken.isUsed()) {
            throw new RuntimeException("Reset token already used");
        }

        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token expired");
        }

        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        resetToken.setUsed(true);

        userRepository.save(user);
        passwordResetTokenRepository.save(resetToken);

        return "Password reset successfully.";
    }
}