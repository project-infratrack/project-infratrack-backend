package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.UserRegistrationRequest;
import com.G153.InfratrackUserPortal.Entities.User;
import com.G153.InfratrackUserPortal.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

}