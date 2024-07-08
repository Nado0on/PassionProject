package com.photo.shoot.repository;

import com.photo.shoot.model.db.PhotoShoot;
import com.photo.shoot.model.db.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing PhotoShoot entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
@Repository
public interface PhotoShootRepository extends JpaRepository<PhotoShoot, String> {

    /**
     * Retrieves a PhotoShoot entity by its ID and status.
     *
     * @param photoShootId ID of the PhotoShoot entity
     * @param status       Status of the PhotoShoot entity
     * @return Optional containing the PhotoShoot entity, or empty if not found
     */
    Optional<PhotoShoot> findByIdAndStatus(String photoShootId, Status status);
}