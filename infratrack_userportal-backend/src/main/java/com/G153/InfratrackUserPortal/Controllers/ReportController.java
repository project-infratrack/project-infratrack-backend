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

    // Endpoint to add a thumbs-up (Prevents duplicate votes)
    @PostMapping("/{id}/thumbs-up")
    public ResponseEntity<String> thumbsUp(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.addThumbsUp(id, userId);
    }

    // Endpoint to add a thumbs-down (Prevents duplicate votes)
    @PostMapping("/{id}/thumbs-down")
    public ResponseEntity<String> thumbsDown(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.addThumbsDown(id, userId);
    }

    // Endpoint to remove thumbs-up
    @PostMapping("/{id}/remove-thumbs-up")
    public ResponseEntity<String> removeThumbsUp(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.removeThumbsUp(id, userId);
    }

    // Endpoint to remove thumbs-down
    @PostMapping("/{id}/remove-thumbs-down")
    public ResponseEntity<String> removeThumbsDown(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.removeThumbsDown(id,userId);
    }
}
