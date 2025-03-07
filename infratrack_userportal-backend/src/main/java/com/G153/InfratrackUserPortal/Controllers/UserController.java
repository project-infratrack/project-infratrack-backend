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
import org.springframework.security.access.prepost.PreAuthorize;
import io.jsonwebtoken.SignatureException;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import com.G153.InfratrackUserPortal.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public UserController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
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