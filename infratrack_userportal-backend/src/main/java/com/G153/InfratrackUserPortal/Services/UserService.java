package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.UserRegistrationRequest;
import com.G153.InfratrackUserPortal.Entities.User;
import com.G153.InfratrackUserPortal.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender; // You'll need to add this dependency


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this. mailSender=  mailSender;
    }

    public User registerUser(UserRegistrationRequest request) {
        // Validate unique fields
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByIdNumber(request.getIdNumber())) {
            throw new RuntimeException("ID Number already exists");
        }

        // Create new user
        User user = new User();
        user.setIdNumber(request.getIdNumber());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMobileNumber(request.getMobileNumber());

        return userRepository.save(user);
    }
    public ResponseEntity<?> loginUser(String idNumber, String password) {
        Optional<User> user = userRepository.findByIdNumber(idNumber);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // Successfully authenticated
            return ResponseEntity.ok("User authenticated successfully");
        }
        // Authentication failed
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID number or password");
    }
    public ResponseEntity<?> initiateForgetPassword(String idNumber) {
        Optional<User> userOptional = userRepository.findByIdNumber(idNumber);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        String otp = generateOTP(); // Method to generate 6-digit OTP

        user.setOtp(passwordEncoder.encode(otp)); // Encode OTP for security
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(15)); // OTP valid for 15 minutes
        userRepository.save(user);

        sendOtpEmail(user.getEmail(), otp);

        return ResponseEntity.ok("OTP sent to registered email");
    }

    public ResponseEntity<?> verifyOtp(String idNumber, String otp) {
        Optional<User> userOptional = userRepository.findByIdNumber(idNumber);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();

        if (user.getOtpExpirationTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("OTP has expired");
        }

        if (!passwordEncoder.matches(otp, user.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        return ResponseEntity.ok("OTP verified successfully");
    }

    public ResponseEntity<?> resetPassword(String idNumber, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        Optional<User> userOptional = userRepository.findByIdNumber(idNumber);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        user.setOtpExpirationTime(null);
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully");
    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@example.com");
        message.setTo(to);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp + "\nThis OTP is valid for 15 minutes.");
        mailSender.send(message);
    }
}
