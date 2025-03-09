package com.G153.InfratrackUserPortal.Controllers;
import com.G153.InfratrackUserPortal.DTO.AdminLoginRequest;
import com.G153.InfratrackUserPortal.Entities.Admin;
import com.G153.InfratrackUserPortal.Services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AdminLoginRequest request) {
        return adminService.loginAdmin(request);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Admin admin) {
        return adminService.registerAdmin(admin);
    }
}
