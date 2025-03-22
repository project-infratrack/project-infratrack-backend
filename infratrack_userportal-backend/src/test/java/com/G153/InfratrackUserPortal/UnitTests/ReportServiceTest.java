package com.G153.InfratrackUserPortal.UnitTests;

import com.G153.InfratrackUserPortal.DTO.ProblemReportDTO;
import com.G153.InfratrackUserPortal.DTO.UserReportDetails;
import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import com.G153.InfratrackUserPortal.Repositories.ProblemReportRepository;
import com.G153.InfratrackUserPortal.Services.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private ProblemReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProblemReport() {
        ProblemReportDTO dto = new ProblemReportDTO();
        dto.setReportType("Type");
        dto.setDescription("Description");
        dto.setLocation("Location");
        dto.setLatitude(0.0);
        dto.setLongitude(0.0);
        dto.setPriorityLevel("High");
        dto.setThumbsUp(0);
        dto.setThumbsDown(0);
        MultipartFile image = mock(MultipartFile.class);
        dto.setImage(image);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user1");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.isAuthenticated()).thenReturn(true);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(reportRepository.save(any(ProblemReport.class))).thenReturn(new ProblemReport());

        ProblemReport savedReport = reportService.saveProblemReport(dto);

        assertNotNull(savedReport);
        verify(reportRepository, times(1)).save(any(ProblemReport.class));
    }
    @Test
    void testGetAllReports() {
        ProblemReport report = new ProblemReport();
        report.setApproval("Accepted"); // Set the approval status to "Accepted"
        List<ProblemReport> reports = List.of(report);
        when(reportRepository.findAll()).thenReturn(reports);

        List<UserReportDetails> result = reportService.getAllReports();

        assertNotNull(result);
        assertEquals(1, result.size(), "Expected a list of size 1, but got: " + result.size());
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetReportDetailsById() {
        String reportId = "123";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        ResponseEntity<UserReportDetails> response = reportService.getReportDetailsById(reportId);

        assertNotNull(response);
        assertEquals(reportId, response.getBody().getId());
    }

    @Test
    void testGetReportDetailsHistoryById() {
        String reportId = "123";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        ResponseEntity<UserReportDetails> response = reportService.getReportDetailsHistoryById(reportId);

        assertNotNull(response);
        assertEquals(reportId, response.getBody().getId());
    }

    @Test
    void testGetReportsByUserNIC() {
        String userNIC = "user1";
        List<ProblemReport> reports = List.of(new ProblemReport());
        when(reportRepository.findByUserId(userNIC)).thenReturn(reports);

        List<ProblemReport> result = reportService.getReportsByUserNIC(userNIC);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testUpdateReportStatus() {
        String reportId = "123";
        String status = "Resolved";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        report.setApproval("Accepted");
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(ProblemReport.class))).thenReturn(report);

        ProblemReport updatedReport = reportService.updateReportStatus(reportId, status);

        assertNotNull(updatedReport);
        assertEquals(status, updatedReport.getStatus());
    }

    @Test
    void testUpdateReportPriorityLevel() {
        String reportId = "123";
        String priorityLevel = "High";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(ProblemReport.class))).thenReturn(report);

        ProblemReport updatedReport = reportService.updateReportPriorityLevel(reportId, priorityLevel);

        assertNotNull(updatedReport);
        assertEquals(priorityLevel, updatedReport.getPriorityLevel());
    }

    @Test
    void testGetPendingReports() {
        List<ProblemReport> reports = List.of(new ProblemReport());
        when(reportRepository.findByPriorityLevelAndApproval("Pending", "Pending")).thenReturn(reports);

        List<ProblemReport> result = reportService.getPendingReports();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testUpdateApprovalStatus() {
        String reportId = "123";
        String approvalStatus = "Accepted";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        ResponseEntity<String> response = reportService.updateApprovalStatus(reportId, approvalStatus);

        assertEquals("Approval status updated!", response.getBody());
    }

    @Test
    void testGetDoneReports() {
        ProblemReport report = new ProblemReport();
        report.setStatus("Done"); // Set the status to "Done"
        report.setApproval("Accepted"); // Set the approval status to "Accepted"
        List<ProblemReport> reports = List.of(report);
        when(reportRepository.findByStatus("Done")).thenReturn(reports);

        List<ProblemReport> result = reportService.getDoneReports();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size()); // Optional: Assert the size of the list
    }

    @Test
    void testGetHighPriorityReports() {
        List<ProblemReport> reports = List.of(new ProblemReport());
        when(reportRepository.findByPriorityLevel("High Priority")).thenReturn(reports);

        List<ProblemReport> result = reportService.getHighPriorityReports();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetMidPriorityReports() {
        List<ProblemReport> reports = List.of(new ProblemReport());
        when(reportRepository.findByPriorityLevel("Mid Priority")).thenReturn(reports);

        List<ProblemReport> result = reportService.getMidPriorityReports();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetLowPriorityReports() {
        List<ProblemReport> reports = List.of(new ProblemReport());
        when(reportRepository.findByPriorityLevel("Low Priority")).thenReturn(reports);

        List<ProblemReport> result = reportService.getLowPriorityReports();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testAddThumbsUp() {
        String reportId = "123";
        String userId = "user1";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        report.setThumbsUp(0);
        // Do NOT add userId to thumbsUpUsers here

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(ProblemReport.class))).thenAnswer(invocation -> {
            ProblemReport savedReport = invocation.getArgument(0);
            assertEquals(1, savedReport.getThumbsUp());
            assertTrue(savedReport.getThumbsUpUsers().contains(userId));
            return savedReport;
        });

        ResponseEntity<String> response = reportService.addThumbsUp(reportId, userId);

        assertEquals("Thumbs up added!", response.getBody());
        // The assertion on the report's state is now within the save mock
    }

    @Test
    void testRemoveThumbsDown() {
        String reportId = "123";
        String userId = "user1";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        report.setThumbsDown(1);
        report.getThumbsDownUsers().add(userId); // Simulate that userId has already voted thumbs down

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(ProblemReport.class))).thenAnswer(invocation -> {
            ProblemReport savedReport = invocation.getArgument(0);
            assertEquals(0, savedReport.getThumbsDown());
            assertFalse(savedReport.getThumbsDownUsers().contains(userId));
            return savedReport;
        });

        ResponseEntity<String> response = reportService.removeThumbsDown(reportId, userId);

        assertEquals("Thumbs down removed!", response.getBody());
        // The assertions on the report's state are now within the save mock
    }

    @Test
    void testRemoveThumbsUp() {
        String reportId = "123";
        String userId = "user1";
        ProblemReport report = new ProblemReport();
        report.setId(reportId);
        report.setThumbsUp(1);
        report.getThumbsUpUsers().add(userId); // Simulate user has already voted thumbs up

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(ProblemReport.class))).thenAnswer(invocation -> {
            ProblemReport savedReport = invocation.getArgument(0);
            assertEquals(0, savedReport.getThumbsUp());
            assertFalse(savedReport.getThumbsUpUsers().contains(userId));
            return savedReport;
        });

        ResponseEntity<String> response = reportService.removeThumbsUp(reportId, userId);

        assertEquals("Thumbs up removed!", response.getBody());
        // The assertions on the report's state are now within the save mock
    }
}