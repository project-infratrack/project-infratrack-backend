package com.G153.InfratrackUserPortal.Repositories;

import com.G153.InfratrackUserPortal.Entities.ProblemReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

/**
 * Repository interface for ProblemReport entities.
 * Extends MongoRepository to provide CRUD operations for ProblemReport entities.
 */
@Repository
public interface ProblemReportRepository extends MongoRepository<ProblemReport, String> {

    /**
     * Finds problem reports by user ID.
     *
     * @param userId the user ID
     * @return a list of problem reports associated with the user ID
     */
    List<ProblemReport> findByUserId(String userId);

    /**
     * Adds a user to the thumbs-up list if they haven't already voted.
     *
     * @param reportId the report ID
     * @param userId the user ID
     */
    @Transactional
    @Query("{ '_id': ?0, 'thumbsUpUsers': { '$ne': ?1 } }")
    @Update("{ '$addToSet': { 'thumbsUpUsers': ?1 }, '$inc': { 'thumbsUp': 1 } }")
    void addThumbsUp(String reportId, String userId);

    /**
     * Adds a user to the thumbs-down list if they haven't already voted.
     *
     * @param reportId the report ID
     * @param userId the user ID
     */
    @Transactional
    @Query("{ '_id': ?0, 'thumbsDownUsers': { '$ne': ?1 } }")
    @Update("{ '$addToSet': { 'thumbsDownUsers': ?1 }, '$inc': { 'thumbsDown': 1 } }")
    void addThumbsDown(String reportId, String userId);

    /**
     * Removes a user from the thumbs-up list and decrements the count.
     *
     * @param reportId the report ID
     * @param userId the user ID
     */
    @Transactional
    @Query("{ '_id': ?0, 'thumbsUpUsers': ?1 }")
    @Update("{ '$pull': { 'thumbsUpUsers': ?1 }, '$inc': { 'thumbsUp': -1 } }")
    void removeThumbsUp(String reportId, String userId);

    /**
     * Removes a user from the thumbs-down list and decrements the count.
     *
     * @param reportId the report ID
     * @param userId the user ID
     */
    @Transactional
    @Query("{ '_id': ?0, 'thumbsDownUsers': ?1 }")
    @Update("{ '$pull': { 'thumbsDownUsers': ?1 }, '$inc': { 'thumbsDown': -1 } }")
    void removeThumbsDown(String reportId, String userId);

    /**
     * Finds problem reports by priority level and approval status.
     *
     * @param priorityLevel the priority level
     * @param approval the approval status
     * @return a list of problem reports matching the priority level and approval status
     */
    List<ProblemReport> findByPriorityLevelAndApproval(String priorityLevel, String approval);

    /**
     * Finds problem reports by status.
     *
     * @param status the status
     * @return a list of problem reports matching the status
     */
    List<ProblemReport> findByStatus(String status);

    /**
     * Finds problem reports by priority level.
     *
     * @param priorityLevel the priority level
     * @return a list of problem reports matching the priority level
     */
    List<ProblemReport> findByPriorityLevel(String priorityLevel);
}