package com.photo.shoot.repository;

import com.photo.shoot.model.db.PhotoShootPayment;
import com.photo.shoot.model.db.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing PhotoShootPayment entities.
 * Extends CrudRepository to inherit basic CRUD operations.
 */
@Repository
public interface PaymentRepository extends CrudRepository<PhotoShootPayment, String> {

    /**
     * Retrieves a PhotoShootPayment entity by its ID and status.
     *
     * @param paymentId ID of the PhotoShootPayment entity
     * @param status    Status of the PhotoShootPayment entity
     * @return Optional containing the PhotoShootPayment entity, or empty if not found
     */
    Optional<PhotoShootPayment> findByIdAndStatus(String paymentId, Status status);

    /**
     * Retrieves all PhotoShootPayment entities by their photo shoot ID and status.
     *
     * @param photoShootId ID of the photo shoot associated with PhotoShootPayment entities
     * @param status       Status of the PhotoShootPayment entities
     * @return List of PhotoShootPayment entities matching the criteria
     */
    List<PhotoShootPayment> findAllByPhotoShootIdAndStatus(String photoShootId, Status status);
}