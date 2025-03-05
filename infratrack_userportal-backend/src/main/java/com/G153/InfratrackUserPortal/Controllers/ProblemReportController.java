package com.G153.InfratrackUserPortal.Controllers;

import com.G153.InfratrackUserPortal.DTO.ProblemReportDTO;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Services.ProblemReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
public class ProblemReportController {

    @Autowired
    private ProblemReportService problemReportService;

    @PostMapping
    public ResponseEntity<ProblemReport> submitProblemReport(
            @Valid @RequestBody ProblemReportDTO problemReportDTO) {
        ProblemReport savedReport = problemReportService.saveProblemReport(problemReportDTO);
        return ResponseEntity.ok(savedReport);
    }
}