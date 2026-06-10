package com.stanionutraul.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String name, String verificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Verify your Nexus Fit account");

        message.setText(
                "Hi " + name + ",\n\n" +
                        "Welcome to Nexus Fit!\n\n" +
                        "Please verify your account by clicking this link:\n" +
                        verificationUrl + "\n\n" +
                        "This link expires in 24 hours.\n\n" +
                        "Nexus Fit Team"
        );

        mailSender.send(message);
    }
}