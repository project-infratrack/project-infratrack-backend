package com.G153.InfratrackUserPortal.Repositories;

import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

@Repository
public interface ProblemReportRepository extends MongoRepository<ProblemReport, String> {
    List<ProblemReport> findByUserId(String userId);

    // Increment thumbs-up count for a report

    @Transactional
    @Query("{ '_id': ?0 }")
    @Update("{ '$inc': { 'thumbsUp': 1 } }")
    void incrementThumbsUp(String id);

    // Increment thumbs-down count for a report

    @Transactional
    @Query("{ '_id': ?0, 'thumbsDown': { '$lt': 2 } }") // Only update if thumbsDown is less than 3
    @Update("{ '$inc': { 'thumbsDown': 1 } }")
    void incrementThumbsDown(String id);

    List<ProblemReport> findByPriorityLevel(String priorityLevel);
}
