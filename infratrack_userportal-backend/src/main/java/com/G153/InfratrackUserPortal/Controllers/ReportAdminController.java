package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.DTO.UserReportDetails;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Services.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling admin-related operations on problem reports.
 */
@RestController
@RequestMapping("/api/admin/report")
public class ReportAdminController {
    private final ReportService reportService;

    /**
     * Constructor for ReportAdminController.
     *
     * @param reportService the report service
     */
    public ReportAdminController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Updates the status of a problem report.
     *
     * @param reportId the ID of the report to update
     * @param status the new status of the report
     * @return a ResponseEntity containing the updated ProblemReport
     */
    @PutMapping("/update-report-status/{reportId}")
    public ResponseEntity<ProblemReport> updateReportStatus(
            @PathVariable String reportId,
            @RequestParam String status) {
        ProblemReport updatedReport = reportService.updateReportStatus(reportId, status);
        return ResponseEntity.ok(updatedReport);
    }

    /**
     * Updates the priority level of a problem report.
     *
     * @param reportId the ID of the report to update
     * @param priorityLevel the new priority level of the report
     * @return a ResponseEntity containing the updated ProblemReport
     */
    @PutMapping("/update-priority-level/{reportId}")
    public ResponseEntity<ProblemReport> updatePriorityLevel(
            @PathVariable String reportId,
            @RequestParam String priorityLevel) {
        ProblemReport updatedReport = reportService.updateReportPriorityLevel(reportId, priorityLevel);
        return ResponseEntity.ok(updatedReport);
    }

    /**
     * Retrieves all pending problem reports.
     *
     * @return a ResponseEntity containing a list of pending ProblemReports
     */
    @GetMapping("/incoming-reports")
    public ResponseEntity<?> getPendingReports() {
        try {
            List<ProblemReport> pendingReports = reportService.getPendingReports();
            return ResponseEntity.ok(pendingReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Updates the approval status of a problem report.
     *
     * @param reportId the ID of the report to update
     * @param approvalStatus the new approval status of the report
     * @return a ResponseEntity containing the result of the update operation
     */
    @PutMapping("/{reportId}/approval")
    public ResponseEntity<String> updateApprovalStatus(@PathVariable String reportId, @RequestParam String approvalStatus) {
        try {
            return reportService.updateApprovalStatus(reportId, approvalStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves all completed problem reports.
     *
     * @return a ResponseEntity containing a list of completed ProblemReports
     */
    @GetMapping("/done-reports")
    public ResponseEntity<?> getDoneReports() {
        try {
            List<ProblemReport> doneReports = reportService.getDoneReports();
            return ResponseEntity.ok(doneReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves all high-priority problem reports.
     *
     * @return a ResponseEntity containing a list of high-priority ProblemReports
     */
    @GetMapping("/high-priority-reports")
    public ResponseEntity<?> getHighPriorityReports() {
        try {
            List<ProblemReport> highPriorityReports = reportService.getHighPriorityReports();
            return ResponseEntity.ok(highPriorityReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves all mid-priority problem reports.
     *
     * @return a ResponseEntity containing a list of mid-priority ProblemReports
     */
    @GetMapping("/mid-priority-reports")
    public ResponseEntity<?> getMidPriorityReports() {
        try {
            List<ProblemReport> midPriorityReports = reportService.getMidPriorityReports();
            return ResponseEntity.ok(midPriorityReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves all low-priority problem reports.
     *
     * @return a ResponseEntity containing a list of low-priority ProblemReports
     */
    @GetMapping("/low-priority-reports")
    public ResponseEntity<?> getLowPriorityReports() {
        try {
            List<ProblemReport> lowPriorityReports = reportService.getLowPriorityReports();
            return ResponseEntity.ok(lowPriorityReports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint for retrieving report details by report ID.
     *
     * @param reportId the report ID
     * @return a ResponseEntity containing the report details if found,
     *         or an error message if the report is not found
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<UserReportDetails> getReportDetailsById(@PathVariable String reportId) {
        return reportService.getReportDetailsById(reportId);
    }
}