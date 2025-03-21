package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.DTO.AdminLoginRequest;
import com.G153.InfratrackUserPortal.Entities.Admin;
import com.G153.InfratrackUserPortal.Services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * Controller for handling admin-related operations such as login and registration.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    /**
     * Constructor for AdminController.
     *
     * @param adminService the admin service
     */
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Endpoint for admin login.
     *
     * @param request the admin login request containing login credentials
     * @return a ResponseEntity with the login result
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AdminLoginRequest request) {
        return adminService.loginAdmin(request);
    }

    /**
     * Endpoint for admin registration.
     *
     * @param admin the admin entity containing registration details
     * @return a ResponseEntity with the registration result
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Admin admin) {
        return adminService.registerAdmin(admin);
    }
}