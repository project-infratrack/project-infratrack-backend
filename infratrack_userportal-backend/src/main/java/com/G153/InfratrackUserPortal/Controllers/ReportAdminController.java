package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Services.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/report")
public class ReportAdminController {
    private final ReportService reportService;

    public ReportAdminController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PutMapping("/update-report-status/{reportId}")
    public ResponseEntity<ProblemReport> updateReportStatus(
            @PathVariable String reportId,
            @RequestParam String status) {
        ProblemReport updatedReport = reportService.updateReportStatus(reportId, status);
        return ResponseEntity.ok(updatedReport);
    }
    @PutMapping("/update-priority-level/{reportId}")
    public ResponseEntity<ProblemReport> updatePriorityLevel(
            @PathVariable String reportId,
            @RequestParam String priorityLevel) {
        ProblemReport updatedReport = reportService.updateReportPriorityLevel(reportId, priorityLevel);
        return ResponseEntity.ok(updatedReport);
    }

    @GetMapping("/incoming-reports")
    public ResponseEntity<List<ProblemReport>> getPendingReports() {
        List<ProblemReport> pendingReports = reportService.getPendingReports();
        return ResponseEntity.ok(pendingReports);
    }

    @GetMapping("/done-reports")
    public ResponseEntity<?> getDoneReports() {
        try {
            List<ProblemReport> doneReports = reportService.getDoneReports();
            return ResponseEntity.ok(doneReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/high-priority-reports")
    public ResponseEntity<?> getHighPriorityReports() {
        try {
            List<ProblemReport> highPriorityReports = reportService.getHighPriorityReports();
            return ResponseEntity.ok(highPriorityReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/mid-priority-reports")
    public ResponseEntity<?> getMidPriorityReports() {
        try {
            List<ProblemReport> midPriorityReports = reportService.getMidPriorityReports();
            return ResponseEntity.ok(midPriorityReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/low-priority-reports")
    public ResponseEntity<?> getLowPriorityReports() {
        try {
            List<ProblemReport> lowPriorityReports = reportService.getLowPriorityReports();
            return ResponseEntity.ok(lowPriorityReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}