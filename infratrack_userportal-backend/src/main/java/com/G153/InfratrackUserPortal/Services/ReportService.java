package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.UserReportDetails;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Repositories.ProblemReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ProblemReportRepository reportRepository;

    @Autowired
    public ReportService(ProblemReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<UserReportDetails> getAllReports() {
        List<ProblemReport> reports = reportRepository.findAll();
        return reports.stream().map(report -> {
            UserReportDetails dto = new UserReportDetails();
            dto.setReportType(report.getReportType());
            dto.setDescription(report.getDescription());
            dto.setLocation(report.getLocation());
            dto.setImage(report.getImage());
            dto.setLatitude(report.getLatitude());
            dto.setLongitude(report.getLongitude());
            return dto;
        }).collect(Collectors.toList());
    }
}