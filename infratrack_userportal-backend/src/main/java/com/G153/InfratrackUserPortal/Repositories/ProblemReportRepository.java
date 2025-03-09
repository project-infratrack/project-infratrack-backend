package com.G153.InfratrackUserPortal.Repositories;

import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemReportRepository extends MongoRepository<ProblemReport, String> {
    List<ProblemReport> findByUserId(String userId);
}