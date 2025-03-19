package com.G153.InfratrackUserPortal.Repositories;

import com.G153.InfratrackUserPortal.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 * Extends MongoRepository to provide CRUD operations for User entities.
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Checks if a user exists by username.
     *
     * @param username the username
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists by email.
     *
     * @param email the email
     * @return true if a user with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists by ID number.
     *
     * @param idNumber the ID number
     * @return true if a user with the given ID number exists, false otherwise
     */
    boolean existsByIdNumber(String idNumber);

    /**
     * Finds a user by ID number.
     *
     * @param idNumber the ID number
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByIdNumber(String idNumber);
}