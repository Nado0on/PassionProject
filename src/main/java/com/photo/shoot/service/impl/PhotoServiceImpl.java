package com.photo.shoot.service.impl;

import com.photo.shoot.dto.ApiResponse;
import com.photo.shoot.exception.NotFoundException;
import com.photo.shoot.exception.RequestValidationException;
import com.photo.shoot.model.db.PhotoShoot;
import com.photo.shoot.model.db.enums.Status;
import com.photo.shoot.repository.PhotoShootRepository;
import com.photo.shoot.service.PhotoShootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service implementation for managing PhotoShoot entities.
 * Implements methods for creating, updating, retrieving, and deleting PhotoShoots.
 */
@Service
public class PhotoServiceImpl implements PhotoShootService {
    private static final Logger logger = Logger.getLogger(PhotoServiceImpl.class.getName());

    @Autowired
    private PhotoShootRepository photoShootRepository;

    /**
     * Creates a new PhotoShoot entry.
     *
     * @param photoShoot The PhotoShoot entity to create
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If creation fails
     */
    @Override
    public ResponseEntity<?> createPhotoShoot(PhotoShoot photoShoot) {
        try {
            photoShoot = photoShootRepository.save(photoShoot);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to create Photo Shoot. " + e);
        }
        ApiResponse<PhotoShoot> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Photo Shoot created successfully");
        List<PhotoShoot> photoShoots = new ArrayList<>();
        photoShoots.add(photoShoot);
        successfulResponse.setData(photoShoots);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Updates an existing PhotoShoot entry.
     *
     * @param photoShootId ID of the PhotoShoot to update
     * @param photoShoot   Updated PhotoShoot entity
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If update fails
     */
    @Override
    public ResponseEntity<?> updatePhotoShoot(String photoShootId, PhotoShoot photoShoot) {
        PhotoShoot existingPhotoShoot = verifyPhotoShoot(photoShootId);
        existingPhotoShoot.setTitle(photoShoot.getTitle());
        existingPhotoShoot.setDescription(photoShoot.getDescription());
        existingPhotoShoot.setStatus(photoShoot.getStatus());
        try {
            existingPhotoShoot = photoShootRepository.save(existingPhotoShoot);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to update Photo Shoot. " + e);
        }
        ApiResponse<PhotoShoot> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Photo Shoot updated successfully");
        List<PhotoShoot> photoShoots = new ArrayList<>();
        photoShoots.add(existingPhotoShoot);
        successfulResponse.setData(photoShoots);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a PhotoShoot entry by its ID.
     *
     * @param photoShootId ID of the PhotoShoot to retrieve
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved PhotoShoot
     */
    @Override
    public ResponseEntity<?> getPhotoShoot(String photoShootId) {
        PhotoShoot photoShoot = verifyPhotoShoot(photoShootId);
        ApiResponse<PhotoShoot> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Photo Shoot successfully");
        List<PhotoShoot> photoShoots = new ArrayList<>();
        photoShoots.add(photoShoot);
        response.setData(photoShoots);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Deletes a PhotoShoot entry by its ID.
     *
     * @param photoShootId ID of the PhotoShoot to delete
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws NotFoundException If PhotoShoot with specified ID is not found
     */
    @Override
    public ResponseEntity<?> deletePhotoShoot(String photoShootId) {
        try {
            PhotoShoot photoShoot = verifyPhotoShoot(photoShootId);
            ApiResponse<?> apiResponse = new ApiResponse<>();
            apiResponse.setCode(HttpStatus.OK.value());
            apiResponse.setMessage("Photo Shoot successfully deleted");
            photoShootRepository.deleteById(photoShootId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Retrieves all Photo Shoot entry.
     *
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved PhotoShoot
     */
    @Override
    public ResponseEntity<?> getAllPhotoShoot() {
        List<PhotoShoot> photoShoots = photoShootRepository.findAll();
        ApiResponse<PhotoShoot> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch All Photo Shoots successfully");
        response.setData(photoShoots);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Verifies if a PhotoShoot with the specified ID exists and is active.
     *
     * @param photoShootId ID of the PhotoShoot to verify
     * @return PhotoShoot entity if found
     * @throws NotFoundException If PhotoShoot with specified ID is not found or not active
     */
    private PhotoShoot verifyPhotoShoot(String photoShootId) {
        Optional<PhotoShoot> optionalPhotoShoot = photoShootRepository.findByIdAndStatus(photoShootId, Status.ACTIVE);
        if (optionalPhotoShoot.isEmpty()) {
            throw new NotFoundException("No entry found for this id: " + photoShootId);
        }
        return optionalPhotoShoot.get();
    }
}