package com.G153.InfratrackUserPortal.Services;

import com.G153.InfratrackUserPortal.DTO.ProblemReportDTO;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Repositories.ProblemReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemReportService {

    @Autowired
    private ProblemReportRepository problemReportRepository;

    public ProblemReport saveProblemReport(ProblemReportDTO dto) {
        ProblemReport problemReport = new ProblemReport();
        problemReport.setReportType(dto.getReportType());
        problemReport.setDescription(dto.getDescription());
        problemReport.setLocation(dto.getLocation());
        problemReport.setImage(dto.getImage());// Set image data
        problemReport.setLatitude(dto.getLatitude());
        problemReport.setLongitude(dto.getLongitude());
        problemReport.setStatus("Pending"); // Default status

        return problemReportRepository.save(problemReport);
    }
}