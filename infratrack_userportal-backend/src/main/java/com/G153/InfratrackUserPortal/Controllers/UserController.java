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

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            User registeredUser = userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Add this method to handle validation exceptions
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
        return userService.loginUser(loginRequest.getIdNumber(), loginRequest.getPassword());
    }
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        return userService.initiateForgetPassword(request.getIdNumber());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        return userService.verifyOtp(request.getIdNumber(), request.getOtp());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return userService.resetPassword(
                request.getIdNumber(),
                request.getNewPassword(),
                request.getConfirmPassword()
        );
    }
}

