package com.photo.shoot.service.impl;

import com.photo.shoot.dto.ApiResponse;
import com.photo.shoot.exception.NotFoundException;
import com.photo.shoot.exception.RequestValidationException;
import com.photo.shoot.model.db.LookBook;
import com.photo.shoot.model.db.PhotoShoot;
import com.photo.shoot.model.db.enums.Status;
import com.photo.shoot.repository.LookBookRepository;
import com.photo.shoot.repository.PhotoShootRepository;
import com.photo.shoot.service.LookBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing LookBook entities.
 * Implements methods for creating, updating, retrieving, and deleting LookBooks.
 */
@Service
public class LookBookServiceImpl implements LookBookService {

    @Autowired
    private LookBookRepository lookBookRepository;

    @Autowired
    private PhotoShootRepository photoShootRepository;

    /**
     * Creates a new LookBook entity.
     *
     * @param lookBook The LookBook entity to create
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If creation fails
     */
    @Override
    public ResponseEntity<?> createLookBook(LookBook lookBook) {
        verifyPhotoShoot(lookBook.getPhotoShootId());
        try {
            lookBook = lookBookRepository.save(lookBook);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to create look book. " + e);
        }
        ApiResponse<LookBook> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Look book created successfully");
        List<LookBook> lookBooks = new ArrayList<>();
        lookBooks.add(lookBook);
        successfulResponse.setData(lookBooks);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Updates an existing LookBook entity.
     *
     * @param lookBookId ID of the LookBook to update
     * @param lookBook   Updated LookBook entity
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If update fails
     */
    @Override
    public ResponseEntity<?> updateLookBook(String lookBookId, LookBook lookBook) {
        LookBook existingLookBook = verifyLookBook(lookBookId);
        verifyPhotoShoot(lookBook.getPhotoShootId());
        existingLookBook.setAuthor1(lookBook.getAuthor1());
        existingLookBook.setAuthor2(lookBook.getAuthor2());
        existingLookBook.setStatus(lookBook.getStatus());
        existingLookBook.setPhotoShootId(lookBook.getPhotoShootId());
        try {
            existingLookBook = lookBookRepository.save(existingLookBook);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to update look book. " + e);
        }
        ApiResponse<LookBook> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Look book updated successfully");
        List<LookBook> lookBooks = new ArrayList<>();
        lookBooks.add(existingLookBook);
        successfulResponse.setData(lookBooks);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a LookBook entity by its ID.
     *
     * @param lookBookId ID of the LookBook to retrieve
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved LookBook
     */
    @Override
    public ResponseEntity<?> getLookBook(String lookBookId) {
        LookBook lookBook = verifyLookBook(lookBookId);
        ApiResponse<LookBook> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Look book successfully");
        List<LookBook> lookBooks = new ArrayList<>();
        lookBooks.add(lookBook);
        response.setData(lookBooks);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Retrieves all Look Book entry.
     *
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved LookBook
     */
    @Override
    public ResponseEntity<?> getAllLookBook() {
        List<LookBook> lookBooks = lookBookRepository.findAll();
        ApiResponse<LookBook> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch All Look Books successfully");
        response.setData(lookBooks);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Deletes a LookBook entity by its ID.
     *
     * @param lookBookId ID of the LookBook to delete
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws NotFoundException If LookBook with specified ID is not found
     */
    @Override
    public ResponseEntity<?> deleteLookBook(String lookBookId) {
        try {
            LookBook lookBook = verifyLookBook(lookBookId);
            ApiResponse<?> apiResponse = new ApiResponse<>();
            apiResponse.setCode(HttpStatus.OK.value());
            apiResponse.setMessage("Look Book successfully deleted");
            lookBookRepository.deleteById(lookBookId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of LookBook entities associated with a specific PhotoShoot ID.
     *
     * @param photoShootId ID of the PhotoShoot
     * @return ResponseEntity containing ApiResponse with status, message, and list of LookBooks
     */
    @Override
    public ResponseEntity<?> getLookBookByPhotoShootId(String photoShootId) {
        verifyPhotoShoot(photoShootId);
        List<LookBook> lookBooksList = lookBookRepository.findAllByPhotoShootIdAndStatus(photoShootId, Status.ACTIVE);
        ApiResponse<LookBook> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Look book list by Photo Shoot id successfully");
        List<LookBook> lookBooks = new ArrayList<>(lookBooksList);
        response.setData(lookBooks);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Verifies if a LookBook with the specified ID exists and is active.
     *
     * @param lookBookId ID of the LookBook to verify
     * @return LookBook entity if found
     * @throws NotFoundException If LookBook with specified ID is not found or not active
     */
    private LookBook verifyLookBook(String lookBookId) {
        Optional<LookBook> optionalLookBook = lookBookRepository.findByIdAndStatus(lookBookId, Status.ACTIVE);
        if (optionalLookBook.isEmpty()) {
            throw new NotFoundException("No Entry Found for this Id: " + lookBookId);
        }
        return optionalLookBook.get();
    }

    /**
     * Verifies if a PhotoShoot with the specified ID exists and is active.
     *
     * @param photoShoot ID of the PhotoShoot to verify
     * @throws NotFoundException If PhotoShoot with specified ID is not found or not active
     */
    private void verifyPhotoShoot(String photoShoot) {
        Optional<PhotoShoot> optionalPhotoShoot = photoShootRepository.findByIdAndStatus(photoShoot, Status.ACTIVE);
        if (optionalPhotoShoot.isEmpty()) {
            throw new NotFoundException("No Entry Found for this Photo Shoot Id: " + photoShoot);
        }
    }
}