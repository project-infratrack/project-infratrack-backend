package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.ProblemReportDTO;
import com.G153.InfratrackUserPortal.DTO.UserReportDetails;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Repositories.ProblemReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing problem reports.
 */
@Service
public class ReportService {
    private final ProblemReportRepository reportRepository;

    /**
     * Constructor to initialize ReportService with required dependencies.
     *
     * @param reportRepository the repository for ProblemReport entities
     */
    @Autowired
    public ReportService(ProblemReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Saves a problem report.
     *
     * @param dto the problem report data transfer object
     * @return the saved problem report
     */
    public ProblemReport saveProblemReport(ProblemReportDTO dto) {
        String userNIC = getUserNIC();
        ProblemReport problemReport = new ProblemReport();
        problemReport.setId(generateReportId());
        problemReport.setReportType(dto.getReportType());
        problemReport.setDescription(dto.getDescription());
        problemReport.setLocation(dto.getLocation());
        problemReport.setLatitude(dto.getLatitude());
        problemReport.setLongitude(dto.getLongitude());
        problemReport.setStatus("Pending");
        problemReport.setUserId(userNIC);
        problemReport.setPriorityLevel(dto.getPriorityLevel()); // Set priority level
        problemReport.setThumbsUp(dto.getThumbsUp());  // Added thumbs-up field
        problemReport.setThumbsDown(dto.getThumbsDown()); // Added thumbs-down field

        // Convert MultipartFile to byte array
        MultipartFile image = dto.getImage();
        if (image != null && !image.isEmpty()) {
            try {
                problemReport.setImage(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert image to byte array", e);
            }
        }

        return reportRepository.save(problemReport);
    }

    /**
     * Generates a unique report ID based on the current date and the number of reports.
     *
     * @return the generated report ID
     */
    private String generateReportId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String datePart = dateFormat.format(new Date());

        List<ProblemReport> reports = reportRepository.findAll();
        long count = reports.stream()
                .filter(report -> report.getId().startsWith(datePart))
                .count();

        String reportNumber = String.format("%02d", count + 1);
        return datePart + reportNumber;
    }

    /**
     * Retrieves all accepted problem reports.
     *
     * @return a list of user report details
     */
    public List<UserReportDetails> getAllReports() {
        List<ProblemReport> reports = reportRepository.findAll();
        return reports.stream()
                .filter(report -> "Accepted".equals(report.getApproval()))
                .map(report -> {
                    UserReportDetails dto = new UserReportDetails();
                    dto.setId(report.getId());
                    dto.setUserId(report.getUserId());
                    dto.setReportType(report.getReportType());
                    dto.setDescription(report.getDescription());
                    dto.setLocation(report.getLocation());
                    dto.setLatitude(report.getLatitude());
                    dto.setLongitude(report.getLongitude());
                    dto.setThumbsUp(report.getThumbsUp());
                    dto.setThumbsDown(report.getThumbsDown());

                    // Convert byte array to Base64 encoded string
                    if (report.getImage() != null) {
                        String base64Image = Base64.getEncoder().encodeToString(report.getImage());
                        dto.setImage(base64Image);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the details of a problem report by its ID.
     *
     * @param reportId the report ID
     * @return a ResponseEntity containing the report details if found,
     *         or an error message if the report is not found
     */
    public ResponseEntity<UserReportDetails> getReportDetailsById(String reportId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();
            UserReportDetails details = new UserReportDetails();
            details.setId(report.getId());
            details.setUserId(report.getUserId());
            details.setReportType(report.getReportType());
            details.setDescription(report.getDescription());
            details.setLocation(report.getLocation());
            details.setLatitude(report.getLatitude());
            details.setLongitude(report.getLongitude());
            details.setThumbsUp(report.getThumbsUp());
            details.setThumbsDown(report.getThumbsDown());

            if (report.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(report.getImage());
                details.setImage(base64Image);
            }

            return ResponseEntity.ok(details);
        } else {
            throw new RuntimeException("Report not found with ID: " + reportId);
        }
    }

    /**
     * Retrieves the details of a problem report by its ID.
     *
     * @param reportId the report ID
     * @return a ResponseEntity containing the report details to history page if found,
     *         or an error message if the report is not found
     */
    public ResponseEntity<UserReportDetails> getReportDetailsHistoryById(String reportId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();
            UserReportDetails details = new UserReportDetails();
            details.setId(report.getId());
            details.setUserId(report.getUserId());
            details.setReportType(report.getReportType());
            details.setDescription(report.getDescription());
            details.setLocation(report.getLocation());
            details.setLatitude(report.getLatitude());
            details.setLongitude(report.getLongitude());
            details.setPriorityLevel(report.getPriorityLevel());
            details.setStatus(report.getStatus());
            details.setThumbsUp(report.getThumbsUp());
            details.setThumbsDown(report.getThumbsDown());


            if (report.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(report.getImage());
                details.setImage(base64Image);
            }

            return ResponseEntity.ok(details);
        } else {
            throw new RuntimeException("Report not found with ID: " + reportId);
        }
    }

    /**
     * Retrieves problem reports by user NIC.
     *
     * @param userNIC the user NIC
     * @return a list of problem reports associated with the user NIC
     */
    public List<ProblemReport> getReportsByUserNIC(String userNIC) {
        return reportRepository.findByUserId(userNIC);
    }

    /**
     * Retrieves the NIC of the currently authenticated user.
     *
     * @return the user NIC
     */
    public String getUserNIC(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    /**
     * Updates the status of a problem report.
     *
     * @param reportId the report ID
     * @param status the new status
     * @return the updated problem report
     */
    public ProblemReport updateReportStatus(String reportId, String status) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();
            if (!"Accepted".equals(report.getApproval())) {
                throw new RuntimeException("Report approval status is not 'Accepted'");
            }
            report.setStatus(status);
            return reportRepository.save(report);
        } else {
            throw new RuntimeException("Report not found with id: " + reportId);
        }
    }

    /**
     * Updates the priority level of a problem report.
     *
     * @param reportId the report ID
     * @param priorityLevel the new priority level
     * @return the updated problem report
     */
    public ProblemReport updateReportPriorityLevel(String reportId, String priorityLevel) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();
            report.setPriorityLevel(priorityLevel);
            return reportRepository.save(report);
        } else {
            throw new RuntimeException("Report not found with id: " + reportId);
        }
    }

    /**
     * Retrieves all pending problem reports.
     *
     * @return a list of pending problem reports
     */
    public List<ProblemReport> getPendingReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevelAndApproval("Pending", "Pending");
        if (reports.isEmpty()) {
            throw new RuntimeException("No Incoming reports found");
        }
        return reports;
    }

    /**
     * Updates the approval status of a problem report.
     *
     * @param reportId the report ID
     * @param approvalStatus the new approval status
     * @return a ResponseEntity containing a success message if the update is successful,
     *         or an error message if the report is not found
     */
    public ResponseEntity<String> updateApprovalStatus(String reportId, String approvalStatus) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();
            report.setApproval(approvalStatus);
            reportRepository.save(report);
            return ResponseEntity.ok("Approval status updated!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }

    /**
     * Retrieves all done problem reports.
     *
     * @return a list of done problem reports
     */
    public List<ProblemReport> getDoneReports() {
        List<ProblemReport> reports = reportRepository.findByStatus("Done");
        List<ProblemReport> filteredReports = reports.stream()
                .filter(report -> "Accepted".equals(report.getApproval()))
                .collect(Collectors.toList());
        if (filteredReports.isEmpty()) {
            throw new RuntimeException("No Reports found in History page");
        }
        return filteredReports;
    }

    /**
     * Retrieves all high priority problem reports.
     *
     * @return a list of high priority problem reports
     */
    public List<ProblemReport> getHighPriorityReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevel("High Priority");
        if (reports.isEmpty()) {
            throw new RuntimeException("No high priority reports found");
        }
        return reports;
    }

    /**
     * Retrieves all mid priority problem reports.
     *
     * @return a list of mid priority problem reports
     */
    public List<ProblemReport> getMidPriorityReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevel("Mid Priority");
        if (reports.isEmpty()) {
            throw new RuntimeException("No mid priority reports found");
        }
        return reports;
    }

    /**
     * Retrieves all low priority problem reports.
     *
     * @return a list of low priority problem reports
     */
    public List<ProblemReport> getLowPriorityReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevel("Low Priority");
        if (reports.isEmpty()) {
            throw new RuntimeException("No low priority reports found");
        }
        return reports;
    }

    /**
     * Adds a thumbs-up vote to a problem report.
     *
     * @param reportId the report ID
     * @param userId the user ID
     * @return a ResponseEntity containing a success message if the thumbs-up is added,
     *         or an error message if the report is not found or the user has already voted
     */
    public ResponseEntity<String> addThumbsUp(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (report.getThumbsDownUsers() != null && report.getThumbsDownUsers().contains(userId)) {
                report.setThumbsDown(report.getThumbsDown() - 1);
                report.getThumbsDownUsers().remove(userId);
            } else if (report.getThumbsUpUsers() != null && report.getThumbsUpUsers().contains(userId)) {
                return ResponseEntity.status(400).body("You have already voted!");
            }

            report.setThumbsUp(report.getThumbsUp() + 1);
            report.getThumbsUpUsers().add(userId);

            reportRepository.save(report);
            return ResponseEntity.ok("Thumbs up added!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }

    /**
     * Adds a thumbs-down vote to a problem report.
     *
     * @param reportId the report ID
     * @param userId the user ID
     * @return a ResponseEntity containing a success message if the thumbs-down is added,
     *         or an error message if the report is not found or the user has already voted
     */
    public ResponseEntity<String> addThumbsDown(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (report.getThumbsUpUsers() != null && report.getThumbsUpUsers().contains(userId)) {
                report.setThumbsUp(report.getThumbsUp() - 1);
                report.getThumbsUpUsers().remove(userId);
            } else if (report.getThumbsDownUsers() != null && report.getThumbsDownUsers().contains(userId)) {
                return ResponseEntity.status(400).body("You have already voted!");
            }

            report.setThumbsDown(report.getThumbsDown() + 1);
            report.getThumbsDownUsers().add(userId);

            if (report.getThumbsDown() >= 5) {
                report.setApproval("Rejected");
            }

            reportRepository.save(report);
            return ResponseEntity.ok("Thumbs down added!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }

    /**
     * Removes a thumbs-up vote from a problem report.
     *
     * @param reportId the report ID
     * @param userId the user ID
     * @return a ResponseEntity containing a success message if the thumbs-up is removed,
     *         or an error message if the report is not found or the user has not voted thumbs-up
     */
    public ResponseEntity<String> removeThumbsUp(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (!report.getThumbsUpUsers().contains(userId)) {
                return ResponseEntity.status(400).body("You have not voted thumbs up!");
            }

            report.setThumbsUp(report.getThumbsUp() - 1);
            report.getThumbsUpUsers().remove(userId);

            reportRepository.save(report);
            return ResponseEntity.ok("Thumbs up removed!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }

    /**
     * Removes a thumbs-down vote from a problem report.
     *
     * @param reportId the report ID
     * @param userId the user ID
     * @return a ResponseEntity containing a success message if the thumbs-down is removed,
     *         or an error message if the report is not found or the user has not voted thumbs-down
     */
    public ResponseEntity<String> removeThumbsDown(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (!report.getThumbsDownUsers().contains(userId)) {
                return ResponseEntity.status(400).body("You have not voted thumbs down!");
            }

            report.setThumbsDown(report.getThumbsDown() - 1);
            report.getThumbsDownUsers().remove(userId);

            if (report.getThumbsDown() < 5) {
                report.setApproval("Accepted");
            }

            reportRepository.save(report);
            return ResponseEntity.ok("Thumbs down removed!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }
}