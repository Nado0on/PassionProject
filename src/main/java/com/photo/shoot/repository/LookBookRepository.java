package com.photo.shoot.repository;

import com.photo.shoot.model.db.LookBook;
import com.photo.shoot.model.db.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing LookBook entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
@Repository
public interface LookBookRepository extends JpaRepository<LookBook, String> {

    /**
     * Retrieves a LookBook entity by its ID and status.
     *
     * @param lookBookId ID of the LookBook entity
     * @param status     Status of the LookBook entity
     * @return Optional containing the LookBook entity, or empty if not found
     */
    Optional<LookBook> findByIdAndStatus(String lookBookId, Status status);

    /**
     * Retrieves all LookBook entities by their photo shoot ID and status.
     *
     * @param photoShootId ID of the photo shoot associated with LookBook entities
     * @param status       Status of the LookBook entities
     * @return List of LookBook entities matching the criteria
     */
    List<LookBook> findAllByPhotoShootIdAndStatus(String photoShootId, Status status);
}