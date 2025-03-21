package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.DTO.*;
import com.G153.InfratrackUserPortal.Entities.User;
import com.G153.InfratrackUserPortal.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;
import com.G153.InfratrackUserPortal.security.JwtTokenProvider;

/**
 * Controller for handling user-related operations such as registration, login, and profile management.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    /**
     * Constructor for UserController.
     *
     * @param userService the user service
     * @param tokenProvider the JWT token provider
     */
    public UserController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Endpoint for user registration.
     *
     * @param request the user registration request containing user details
     * @return a ResponseEntity with the registration result
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            User registeredUser = userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Handles validation exceptions.
     *
     * @param ex the MethodArgumentNotValidException
     * @return a map containing field errors and their messages
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Endpoint for user login.
     *
     * @param loginRequest the user login request containing login credentials
     * @return a ResponseEntity with the login result
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        return userService.loginUser(loginRequest.getIdNumber(), loginRequest.getPassword());
    }

    /**
     * Endpoint for initiating the forget password process.
     *
     * @param request the forget password request containing user ID number
     * @return a ResponseEntity with the result of the forget password process
     */
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        return userService.initiateForgetPassword(request.getIdNumber());
    }

    /**
     * Endpoint for verifying the OTP.
     *
     * @param request the OTP verification request containing user ID number and OTP
     * @return a ResponseEntity with the result of the OTP verification
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        return userService.verifyOtp(request.getIdNumber(), request.getOtp());
    }

    /**
     * Endpoint for resetting the password.
     *
     * @param request the reset password request containing user ID number, new password, and confirm password
     * @return a ResponseEntity with the result of the password reset
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(
                request.getIdNumber(),
                request.getNewPassword(),
                request.getConfirmPassword()
        );
    }

    /**
     * Endpoint for retrieving the user profile.
     *
     * @param token the JWT token from the request header
     * @return a ResponseEntity containing the user profile details
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDetails> getUserProfile(@RequestHeader("Authorization") String token) {
        String userId = tokenProvider.getUserIdFromJWT(token.substring(7)); // Remove "Bearer " prefix
        UserProfileDetails userProfileDetails = userService.getUserProfileDetails(userId);
        return ResponseEntity.ok(userProfileDetails);
    }
}