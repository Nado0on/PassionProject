package com.photo.shoot.repository;

import com.photo.shoot.model.db.PhotoShootSchedule;
import com.photo.shoot.model.db.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing PhotoShootSchedule entities.
 * Extends CrudRepository to inherit basic CRUD operations.
 */
@Repository
public interface ScheduleRepository extends CrudRepository<PhotoShootSchedule, String> {

    /**
     * Retrieves a PhotoShootSchedule entity by its ID and status.
     *
     * @param scheduleId ID of the PhotoShootSchedule entity
     * @param status     Status of the PhotoShootSchedule entity
     * @return Optional containing the PhotoShootSchedule entity, or empty if not found
     */
    Optional<PhotoShootSchedule> findByIdAndStatus(String scheduleId, Status status);

    /**
     * Retrieves all PhotoShootSchedule entities associated with a specific photo shoot ID and status.
     *
     * @param photoShootId ID of the photo shoot
     * @param status       Status of the PhotoShootSchedule entities
     * @return List of PhotoShootSchedule entities
     */
    List<PhotoShootSchedule> findAllByPhotoShootIdAndStatus(String photoShootId, Status status);
}