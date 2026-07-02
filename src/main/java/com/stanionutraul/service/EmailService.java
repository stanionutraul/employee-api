package com.stanionutraul.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${resend.api.key}")
    private String resendApiKey;

    private static final String FROM_EMAIL =
            "Nexus Fit <onboarding@resend.dev>";

    public void sendVerificationEmail(
            String to,
            String name,
            String verificationUrl
    ) {
        String html = """
                <div style="font-family: Arial, sans-serif; background:#0f172a; padding:40px; color:white;">
                    <div style="max-width:600px; margin:auto; background:#111827; border-radius:20px; padding:40px;">
                        <h1 style="color:#8b5cf6; margin-bottom:20px;">
                            Welcome to Nexus Fit 💪
                        </h1>

                        <p style="font-size:16px;">Hi %s,</p>

                        <p style="color:#cbd5e1; line-height:1.7;">
                            Thanks for creating your account.
                            Please verify your email address to activate your Nexus Fit profile.
                        </p>

                        <div style="margin:35px 0;">
                            <a href="%s"
                               style="background:#8b5cf6; color:white; padding:14px 26px; text-decoration:none; border-radius:12px; font-weight:bold;">
                               Verify Email
                            </a>
                        </div>

                        <p style="color:#94a3b8; font-size:14px;">
                            This verification link expires in 24 hours.
                        </p>

                        <hr style="border:none; border-top:1px solid #1e293b; margin:25px 0;">

                        <p style="color:#64748b; font-size:13px;">
                            Nexus Fit Team
                        </p>
                    </div>
                </div>
                """.formatted(name, verificationUrl);

        sendEmail(to, "Verify your Nexus Fit account", html);
    }

    public void sendPasswordResetEmail(
            String to,
            String name,
            String resetUrl
    ) {
        String html = """
                <div style="font-family: Arial, sans-serif; background:#0f172a; padding:40px; color:white;">
                    <div style="max-width:600px; margin:auto; background:#111827; border-radius:20px; padding:40px;">
                        <h1 style="color:#8b5cf6; margin-bottom:20px;">
                            Reset your password 🔐
                        </h1>

                        <p style="font-size:16px;">Hi %s,</p>

                        <p style="color:#cbd5e1; line-height:1.7;">
                            We received a request to reset your Nexus Fit password.
                            Click the button below to create a new password.
                        </p>

                        <div style="margin:35px 0;">
                            <a href="%s"
                               style="background:#8b5cf6; color:white; padding:14px 26px; text-decoration:none; border-radius:12px; font-weight:bold;">
                               Reset Password
                            </a>
                        </div>

                        <p style="color:#94a3b8; font-size:14px;">
                            This password reset link expires in 30 minutes.
                        </p>

                        <hr style="border:none; border-top:1px solid #1e293b; margin:25px 0;">

                        <p style="color:#64748b; font-size:13px;">
                            Nexus Fit Team
                        </p>
                    </div>
                </div>
                """.formatted(name, resetUrl);

        sendEmail(to, "Reset your Nexus Fit password", html);
    }

    private void sendEmail(String to, String subject, String html) {
        try {
            Resend resend = new Resend(resendApiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(FROM_EMAIL)
                    .to(to)
                    .subject(subject)
                    .html(html)
                    .build();

            CreateEmailResponse response = resend.emails().send(params);

            System.out.println("Email sent with Resend ID: " + response.getId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email with Resend: " + e.getMessage(), e);
        }
    }
}