package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.JwtAuthResponse;
import com.G153.InfratrackUserPortal.DTO.UserProfileDetails;
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

/**
 * Service class for managing User operations.
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender mailSender;

    /**
     * Constructor to initialize UserService with required dependencies.
     *
     * @param userRepository the repository for User entities
     * @param passwordEncoder the password encoder
     * @param jwtTokenProvider the JWT token provider
     * @param mailSender the mail sender
     */
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider,
                       JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailSender = mailSender;
    }

    /**
     * Registers a new user by saving their details in the repository.
     *
     * @param request the user registration request containing user details
     * @return the registered user
     */
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

    /**
     * Authenticates a user and generates a JWT token if credentials are valid.
     *
     * @param idNumber the user's ID number
     * @param password the user's password
     * @return a ResponseEntity containing the JWT token if authentication is successful,
     *         or an error message if authentication fails
     */
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

    /**
     * Initiates the forget password process by generating and sending an OTP.
     *
     * @param idNumber the user's ID number
     * @return a ResponseEntity containing a success message if the OTP is sent,
     *         or an error message if the user is not found
     */
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

    /**
     * Verifies the OTP for password reset.
     *
     * @param idNumber the user's ID number
     * @param otp the OTP
     * @return a ResponseEntity containing a success message if the OTP is valid,
     *         or an error message if the OTP is invalid or expired
     */
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

    /**
     * Resets the user's password.
     *
     * @param idNumber the user's ID number
     * @param newPassword the new password
     * @param confirmPassword the confirmation of the new password
     * @return a ResponseEntity containing a success message if the password is reset,
     *         or an error message if the passwords do not match or the user is not found
     */
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

    /**
     * Generates a 6-digit OTP.
     *
     * @return the generated OTP
     */
    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    /**
     * Sends an OTP email to the user.
     *
     * @param to the recipient's email address
     * @param otp the OTP
     */
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

    /**
     * Retrieves a user by their ID number.
     *
     * @param idNumber the user's ID number
     * @return an Optional containing the user if found, or empty if not found
     */
    public Optional<User> getUserByIdNumber(String idNumber) {
        return userRepository.findByIdNumber(idNumber);
    }

    /**
     * Retrieves the profile details of a user.
     *
     * @param userId the user's ID number
     * @return the user's profile details
     */
    public UserProfileDetails getUserProfileDetails(String userId) {
        User user = userRepository.findByIdNumber(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserProfileDetails userProfileDetails = new UserProfileDetails();
        userProfileDetails.setName(user.getFirstName() + " " + user.getLastName());
        userProfileDetails.setIdNumber(user.getIdNumber());
        userProfileDetails.setUsername(user.getUsername());
        userProfileDetails.setEmail(user.getEmail());
        userProfileDetails.setMobileNo(user.getMobileNumber());
        return userProfileDetails;
    }
}