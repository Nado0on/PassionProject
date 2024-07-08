package com.photo.shoot.repository;

import com.photo.shoot.model.db.PhotoShootUpload;
import com.photo.shoot.model.db.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing PhotoShootUpload entities.
 * Extends CrudRepository to inherit basic CRUD operations.
 */
@Repository
public interface UploadRepository extends CrudRepository<PhotoShootUpload, String> {

    /**
     * Retrieves a PhotoShootUpload entity by its ID and status.
     *
     * @param uploadId ID of the PhotoShootUpload entity
     * @param status   Status of the PhotoShootUpload entity
     * @return Optional containing the PhotoShootUpload entity, or empty if not found
     */
    Optional<PhotoShootUpload> findByIdAndStatus(String uploadId, Status status);

    /**
     * Retrieves all PhotoShootUpload entities associated with a specific look book ID and status.
     *
     * @param lookBookId ID of the look book
     * @param status     Status of the PhotoShootUpload entities
     * @return List of PhotoShootUpload entities
     */
    List<PhotoShootUpload> findAllByLookBookIdAndStatus(String lookBookId, Status status);
}