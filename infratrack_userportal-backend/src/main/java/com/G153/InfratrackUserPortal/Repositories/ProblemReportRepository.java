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

    // Add user to thumbs-up list if they haven't already voted
    @Transactional
    @Query("{ '_id': ?0, 'thumbsUpUsers': { '$ne': ?1 } }")
    @Update("{ '$addToSet': { 'thumbsUpUsers': ?1 }, '$inc': { 'thumbsUp': 1 } }")
    void addThumbsUp(String reportId, String userId);

    // Add user to thumbs-down list if they haven't already voted
    @Transactional
    @Query("{ '_id': ?0, 'thumbsDownUsers': { '$ne': ?1 } }")
    @Update("{ '$addToSet': { 'thumbsDownUsers': ?1 }, '$inc': { 'thumbsDown': 1 } }")
    void addThumbsDown(String reportId, String userId);

    // Remove user from thumbs-up list and decrement count
    @Transactional
    @Query("{ '_id': ?0, 'thumbsUpUsers': ?1 }")
    @Update("{ '$pull': { 'thumbsUpUsers': ?1 }, '$inc': { 'thumbsUp': -1 } }")
    void removeThumbsUp(String reportId, String userId);

    // Remove user from thumbs-down list and decrement count
    @Transactional
    @Query("{ '_id': ?0, 'thumbsDownUsers': ?1 }")
    @Update("{ '$pull': { 'thumbsDownUsers': ?1 }, '$inc': { 'thumbsDown': -1 } }")
    void removeThumbsDown(String reportId, String userId);

    List<ProblemReport> findByPriorityLevelAndApproval(String priorityLevel, String approval);;

    List<ProblemReport> findByStatus(String done);

    List<ProblemReport> findByPriorityLevel(String priorityLevel);
}