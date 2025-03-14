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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        problemReport.setThumbsUp(dto.getThumbsUp());  // ✅ Added thumbs-up field
        problemReport.setThumbsDown(dto.getThumbsDown()); // ✅ Added thumbs-down field

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

    // ✅ Method to add thumbs-up to a report
    public void addThumbsUp(String reportId) {
        reportRepository.incrementThumbsUp(reportId);
    }

    // ✅ Method to add thumbs-down to a report
    public void addThumbsDown(String reportId) {
        reportRepository.incrementThumbsDown(reportId);
    }

    public List<ProblemReport> getReportsByPriority(String priorityLevel) {
        return reportRepository.findByPriorityLevel(priorityLevel);
    }

}