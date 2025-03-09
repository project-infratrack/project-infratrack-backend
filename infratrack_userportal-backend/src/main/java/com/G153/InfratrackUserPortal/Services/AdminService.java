package com.G153.InfratrackUserPortal.Services;
import com.G153.InfratrackUserPortal.DTO.AdminLoginRequest;
import com.G153.InfratrackUserPortal.Entities.Admin;
import com.G153.InfratrackUserPortal.Repositories.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> loginAdmin(AdminLoginRequest request) {
        Optional<Admin> adminOptional = adminRepository.findByAdminNo(request.getAdminNo());

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        }
        return ResponseEntity.status(404).body("Admin not found");
    }

    public ResponseEntity<String> registerAdmin(Admin admin) {
        if (adminRepository.findByAdminNo(admin.getAdminNo()).isPresent()) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));  // Hash password
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }
}

