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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ProblemReportRepository reportRepository;

    @Autowired
    public ReportService(ProblemReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ProblemReport saveProblemReport(ProblemReportDTO dto) {
        String userNIC = getUserNIC();
        ProblemReport problemReport = new ProblemReport();
        problemReport.setId(generateReportId());
        problemReport.setReportType(dto.getReportType());
        problemReport.setDescription(dto.getDescription());
        problemReport.setLocation(dto.getLocation());
        problemReport.setImage(dto.getImage());
        problemReport.setLatitude(dto.getLatitude());
        problemReport.setLongitude(dto.getLongitude());
        problemReport.setStatus("Pending");
        problemReport.setUserId(userNIC);
        problemReport.setPriorityLevel(dto.getPriorityLevel()); // Set priority level
        problemReport.setThumbsUp(dto.getThumbsUp());  // Added thumbs-up field
        problemReport.setThumbsDown(dto.getThumbsDown()); // Added thumbs-down field

        return reportRepository.save(problemReport);
    }

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

    public List<UserReportDetails> getAllReports() {
        List<ProblemReport> reports = reportRepository.findAll();
        return reports.stream().map(report -> {
            UserReportDetails dto = new UserReportDetails();
            dto.setUserId(report.getUserId());
            dto.setReportType(report.getReportType());
            dto.setDescription(report.getDescription());
            dto.setLocation(report.getLocation());
            dto.setImage(report.getImage());
            dto.setLatitude(report.getLatitude());
            dto.setLongitude(report.getLongitude());
            dto.setThumbsUp(report.getThumbsUp());
            dto.setThumbsDown(report.getThumbsDown());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ProblemReport> getReportsByUserNIC(String userNIC) {
        return reportRepository.findByUserId(userNIC);
    }

    public String getUserNIC(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public ProblemReport updateReportStatus(String reportId, String status) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();
            report.setStatus(status);
            return reportRepository.save(report);
        } else {
            throw new RuntimeException("Report not found with id: " + reportId);
        }
    }

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

    public List<ProblemReport> getPendingReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevel("Pending");
        if (reports.isEmpty()) {
            throw new RuntimeException("No Incoming reports found");
        }
        return reports;
    }

    public List<ProblemReport> getDoneReports() {
        List<ProblemReport> reports = reportRepository.findByStatus("Done");
        if (reports.isEmpty()) {
            throw new RuntimeException("No Reports found in History page ");
        }
        return reports;
    }

    public List<ProblemReport> getHighPriorityReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevel("High Priority");
        if (reports.isEmpty()) {
            throw new RuntimeException("No high priority reports found");
        }
        return reports;
    }

    public List<ProblemReport> getMidPriorityReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevel("Mid Priority");
        if (reports.isEmpty()) {
            throw new RuntimeException("No mid priority reports found");
        }
        return reports;
    }

    public List<ProblemReport> getLowPriorityReports() {
        List<ProblemReport> reports = reportRepository.findByPriorityLevel("Low Priority");
        if (reports.isEmpty()) {
            throw new RuntimeException("No low priority reports found");
        }
        return reports;
    }
    // Add thumbs-up if the user hasn't already up-voted
    public ResponseEntity<String> addThumbsUp(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (report.getThumbsUpUsers() == null) {
                report.setThumbsUpUsers(new HashSet<>());
            }
            if (report.getThumbsDownUsers() == null) {
                report.setThumbsDownUsers(new HashSet<>());
            }

            if (report.getThumbsUpUsers().contains(userId)) {
                System.out.println("ERROR: User " + userId + " has ALREADY up-voted this report ID: " + reportId);

                return ResponseEntity.status(400).body("You have already up-voted this report!");
            }

            // Remove from thumbsDown if previously down-voted
            if (report.getThumbsDownUsers().contains(userId)) {
                report.getThumbsDownUsers().remove(userId);
                report.setThumbsDown(report.getThumbsDown() > 0 ? report.getThumbsDown() - 1 : 0);
            }

            report.getThumbsUpUsers().add(userId);
            report.setThumbsUp(report.getThumbsUp() + 1);
            reportRepository.save(report);

            return ResponseEntity.ok("Thumbs up added!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }

    // Add thumbs-down if the user hasn't already down-voted
    public ResponseEntity<String> addThumbsDown(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (report.getThumbsUpUsers() == null) {
                report.setThumbsUpUsers(new HashSet<>());
            }
            if (report.getThumbsDownUsers() == null) {
                report.setThumbsDownUsers(new HashSet<>());
            }

            if (report.getThumbsDownUsers().contains(userId)) {
                // LOGGING THE ERROR
                System.out.println("ERROR: User " + userId + " has ALREADY down-voted this report ID: " + reportId);

                return ResponseEntity.status(400).body("You have already down-voted this report!");
            }

            // Remove from thumbsUp if previously up-voted
            if (report.getThumbsUpUsers().contains(userId)) {
                report.getThumbsUpUsers().remove(userId);
                report.setThumbsUp(report.getThumbsUp() > 0 ? report.getThumbsUp() - 1 : 0);
            }

            report.getThumbsDownUsers().add(userId);
            report.setThumbsDown(report.getThumbsDown() + 1);
            reportRepository.save(report);

            return ResponseEntity.ok("Thumbs down added!");
        }

        return ResponseEntity.badRequest().body("Report not found!");
    }

    // Remove thumbs-up if the user previously voted
    public ResponseEntity<String> removeThumbsUp(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (report.getThumbsUpUsers() == null) {
                report.setThumbsUpUsers(new HashSet<>());
            }

            if (report.getThumbsUpUsers().contains(userId)) {
                report.getThumbsUpUsers().remove(userId);
                report.setThumbsUp(report.getThumbsUp() > 0 ? report.getThumbsUp() - 1 : 0);
                reportRepository.save(report);
                return ResponseEntity.ok("Thumbs up removed!");
            }
            return ResponseEntity.status(400).body("You haven't up-voted this report!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }


    // Remove thumbs-down if the user previously voted
    public ResponseEntity<String> removeThumbsDown(String reportId, String userId) {
        Optional<ProblemReport> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            ProblemReport report = reportOptional.get();

            if (report.getThumbsDownUsers() == null) {
                report.setThumbsDownUsers(new HashSet<>());
            }

            if (report.getThumbsDownUsers().contains(userId)) {
                report.getThumbsDownUsers().remove(userId);
                report.setThumbsDown(report.getThumbsDown() > 0 ? report.getThumbsDown() - 1 : 0);
                reportRepository.save(report);
                return ResponseEntity.ok("Thumbs down removed!");
            }
            return ResponseEntity.status(400).body("You haven't down-voted this report!");
        }
        return ResponseEntity.badRequest().body("Report not found!");
    }

}