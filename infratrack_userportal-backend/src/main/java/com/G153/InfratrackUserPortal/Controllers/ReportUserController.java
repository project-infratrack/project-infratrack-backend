package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.DTO.ProblemReportDTO;
import com.G153.InfratrackUserPortal.DTO.UserReportDetails;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user-related operations on problem reports.
 */
@RestController
@RequestMapping("/api/users/report")
public class ReportUserController {
    private final ReportService reportService;

    /**
     * Constructor for ReportUserController.
     *
     * @param reportService the report service
     */
    @Autowired
    public ReportUserController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Endpoint for submitting a problem report.
     *
     * @param problemReportDTO the problem report data transfer object
     * @return a ResponseEntity containing the saved ProblemReport
     */
    @PostMapping("/submit")
    public ResponseEntity<ProblemReport> submitProblemReport(@ModelAttribute ProblemReportDTO problemReportDTO) {
        ProblemReport savedReport = reportService.saveProblemReport(problemReportDTO);
        return ResponseEntity.ok(savedReport);
    }

    /**
     * Endpoint for retrieving all problem reports.
     *
     * @return a ResponseEntity containing a list of UserReportDetails
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<UserReportDetails>> getAllReports() {
        List<UserReportDetails> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    /**
     * Endpoint for retrieving problem reports by user NIC.
     *
     * @return a ResponseEntity containing a list of ProblemReports or an error message if no reports are found
     */
    @GetMapping("/get-by-user")
    public ResponseEntity<?> getReportsByUserNIC() {
        String userNIC = reportService.getUserNIC();
        List<ProblemReport> reports = reportService.getReportsByUserNIC(userNIC);
        if (reports.isEmpty()) {
            return ResponseEntity.status(404).body("No reports found for user with NIC: " + userNIC);
        }
        return ResponseEntity.ok(reports);
    }

    /**
     * Endpoint to add a thumbs-up to a problem report (prevents duplicate votes).
     *
     * @param id the ID of the problem report
     * @return a ResponseEntity containing the result of the thumbs-up operation
     */
    @PostMapping("/{id}/thumbs-up")
    public ResponseEntity<String> thumbsUp(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.addThumbsUp(id, userId);
    }

    /**
     * Endpoint to add a thumbs-down to a problem report (prevents duplicate votes).
     *
     * @param id the ID of the problem report
     * @return a ResponseEntity containing the result of the thumbs-down operation
     */
    @PostMapping("/{id}/thumbs-down")
    public ResponseEntity<String> thumbsDown(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.addThumbsDown(id, userId);
    }

    /**
     * Endpoint to remove a thumbs-up from a problem report.
     *
     * @param id the ID of the problem report
     * @return a ResponseEntity containing the result of the remove thumbs-up operation
     */
    @PostMapping("/{id}/remove-thumbs-up")
    public ResponseEntity<String> removeThumbsUp(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.removeThumbsUp(id, userId);
    }

    /**
     * Endpoint to remove a thumbs-down from a problem report.
     *
     * @param id the ID of the problem report
     * @return a ResponseEntity containing the result of the remove thumbs-down operation
     */
    @PostMapping("/{id}/remove-thumbs-down")
    public ResponseEntity<String> removeThumbsDown(@PathVariable String id) {
        String userId = reportService.getUserNIC();
        return reportService.removeThumbsDown(id, userId);
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
    @GetMapping("history/{reportId}")
    public ResponseEntity<UserReportDetails> getReportDetailsHistoryById(@PathVariable String reportId) {
        return reportService.getReportDetailsHistoryById(reportId);
    }
}