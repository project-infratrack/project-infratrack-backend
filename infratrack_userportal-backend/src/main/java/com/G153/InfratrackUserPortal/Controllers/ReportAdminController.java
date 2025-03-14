package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Services.ReportService;
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

    @GetMapping("/priority-high")
    public ResponseEntity<List<ProblemReport>> getHighPriorityReports() {
        List<ProblemReport> reports = reportService.getReportsByPriority("High");
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/priority-mid")
    public ResponseEntity<List<ProblemReport>> getMidPriorityReports() {
        List<ProblemReport> reports = reportService.getReportsByPriority("Mid");
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/priority-low")
    public ResponseEntity<List<ProblemReport>> getLowPriorityReports() {
        List<ProblemReport> reports = reportService.getReportsByPriority("Low");
        return ResponseEntity.ok(reports);
    }
}