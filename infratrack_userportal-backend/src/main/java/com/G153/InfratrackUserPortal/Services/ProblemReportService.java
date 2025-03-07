package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.ProblemReportDTO;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Repositories.ProblemReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProblemReportService {

    @Autowired
    private ProblemReportRepository problemReportRepository;

    public ProblemReport saveProblemReport(ProblemReportDTO dto) {
        ProblemReport problemReport = new ProblemReport();
        problemReport.setId(generateReportId());
        problemReport.setReportType(dto.getReportType());
        problemReport.setDescription(dto.getDescription());
        problemReport.setLocation(dto.getLocation());
        problemReport.setImage(dto.getImage());
        problemReport.setLatitude(dto.getLatitude());
        problemReport.setLongitude(dto.getLongitude());
        problemReport.setStatus("Pending");

        return problemReportRepository.save(problemReport);
    }

    private String generateReportId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String datePart = dateFormat.format(new Date());

        List<ProblemReport> reports = problemReportRepository.findAll();
        long count = reports.stream()
                .filter(report -> report.getId().startsWith(datePart))
                .count();

        String reportNumber = String.format("%02d", count + 1);
        return datePart + reportNumber;
    }
}