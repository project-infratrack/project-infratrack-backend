package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.DTO.ProblemReportDTO;
import com.G153.InfratrackUserPortal.DTO.UserReportDetails;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Services.ReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/report")
public class ReportController {
    private final ReportService reportService;


    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/submit")
    public ResponseEntity<ProblemReport> submitProblemReport(
            @Valid @RequestBody ProblemReportDTO problemReportDTO) {
        ProblemReport savedReport = reportService.saveProblemReport(problemReportDTO);
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserReportDetails>> getAllReports() {
        List<UserReportDetails> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/get-by-user")
    public List<ProblemReport> getReportsByUserNIC(){
        String userNIC = reportService.getUserNIC();
        return reportService.getReportsByUserNIC(userNIC);
    }
}