package com.G153.InfratrackUserPortal.Repositories;

import com.G153.InfratrackUserPortal.Entities.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * Repository interface for Admin entities.
 * Extends MongoRepository to provide CRUD operations for Admin entities.
 */
public interface AdminRepository extends MongoRepository<Admin, String> {

    /**
     * Finds an Admin by their admin number.
     *
     * @param adminNo the admin number
     * @return an Optional containing the Admin if found, or empty if not found
     */
    Optional<Admin> findByAdminNo(String adminNo);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Admin> findByUsername(String username);
}