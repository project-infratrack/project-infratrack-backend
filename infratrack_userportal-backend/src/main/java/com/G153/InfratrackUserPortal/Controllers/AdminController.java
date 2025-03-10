package com.G153.InfratrackUserPortal.Controllers;
import com.G153.InfratrackUserPortal.DTO.AdminLoginRequest;
import com.G153.InfratrackUserPortal.Entities.Admin;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.G153.InfratrackUserPortal.Services.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final ReportService reportService;

    public AdminController(AdminService adminService, ReportService reportService) {
        this.adminService = adminService;
        this.reportService = reportService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AdminLoginRequest request) {
        return adminService.loginAdmin(request);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Admin admin) {
        return adminService.registerAdmin(admin);
    }

    @PutMapping("/update-report-status/{reportId}")
    public ResponseEntity<ProblemReport> updateReportStatus(
            @PathVariable String reportId,
            @RequestParam String status) {
        ProblemReport updatedReport = reportService.updateReportStatus(reportId, status);
        return ResponseEntity.ok(updatedReport);
    }
}
