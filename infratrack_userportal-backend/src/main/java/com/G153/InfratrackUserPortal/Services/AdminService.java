package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.AdminLoginRequest;
import com.G153.InfratrackUserPortal.DTO.JwtAuthResponse;
import com.G153.InfratrackUserPortal.Entities.Admin;
import com.G153.InfratrackUserPortal.Repositories.AdminRepository;
import com.G153.InfratrackUserPortal.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service class for managing Admin operations.
 */
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor to initialize AdminService with required dependencies.
     *
     * @param adminRepository the repository for Admin entities
     * @param passwordEncoder the password encoder
     * @param jwtTokenProvider the JWT token provider
     */
    public AdminService(AdminRepository adminRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider jwtTokenProvider) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Authenticates an admin and generates a JWT token if credentials are valid.
     *
     * @param request the admin login request containing admin number and password
     * @return a ResponseEntity containing the JWT token if authentication is successful,
     *         or an error message if authentication fails
     */
    public ResponseEntity<?> loginAdmin(AdminLoginRequest request) {
        Optional<Admin> adminOptional = adminRepository.findByAdminNo(request.getAdminNo());
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                String token = jwtTokenProvider.generateToken(admin.getAdminNo());
                return ResponseEntity.ok(new JwtAuthResponse(token));
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        }
        return ResponseEntity.status(404).body("Admin not found");
    }

    /**
     * Registers a new admin by saving their details in the repository.
     *
     * @param admin the admin entity to be registered
     * @return a ResponseEntity containing a success message if registration is successful,
     *         or an error message if the admin already exists
     */
    public ResponseEntity<String> registerAdmin(Admin admin) {
        if (adminRepository.findByAdminNo(admin.getAdminNo()).isPresent()) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));  // Hash password
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }
}