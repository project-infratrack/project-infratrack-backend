package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.JwtAuthResponse;
import com.G153.InfratrackUserPortal.DTO.UserRegistrationRequest;
import com.G153.InfratrackUserPortal.Entities.User;
import com.G153.InfratrackUserPortal.Repositories.UserRepository;
import com.G153.InfratrackUserPortal.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender mailSender;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailSender = mailSender;
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
        Optional<User> userOptional = userRepository.findByIdNumber(idNumber);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid ID number or password");
        }

        User user = userOptional.get();
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Generate JWT token
            String token = jwtTokenProvider.generateToken(user.getIdNumber());
            return ResponseEntity.ok(new JwtAuthResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid ID number or password");
    }

    public ResponseEntity<?> initiateForgetPassword(String idNumber) {
        Optional<User> userOptional = userRepository.findByIdNumber(idNumber);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        String otp = generateOTP();

        user.setOtp(passwordEncoder.encode(otp));
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        // Send OTP via email
        sendOtpEmail(user.getEmail(), otp);

        return ResponseEntity.ok("OTP has been sent to your registered email");
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

        // Generate token after OTP verification
        String token = jwtTokenProvider.generateToken(user.getIdNumber());
        return ResponseEntity.ok(new JwtAuthResponse(token));
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

        String token = jwtTokenProvider.generateToken(user.getIdNumber());
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("infratrackiit@gmail.com");
        message.setTo(to);
        message.setSubject("Infratrack - Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp + "\n\n" +
                "This OTP is valid for 15 minutes.\n\n" +
                "If you did not request this password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Infratrack Team");
        mailSender.send(message);
    }

    public Optional<User> getUserByIdNumber(String idNumber) {
        return userRepository.findByIdNumber(idNumber);
    }
}