package com.G153.InfratrackUserPortal.Repositories;
import com.G153.InfratrackUserPortal.Entities.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByAdminNo(String adminNo);
}
