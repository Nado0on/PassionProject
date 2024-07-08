package com.photo.shoot.service.impl;

import com.photo.shoot.dto.ApiResponse;
import com.photo.shoot.exception.NotFoundException;
import com.photo.shoot.exception.RequestValidationException;
import com.photo.shoot.model.db.LookBook;
import com.photo.shoot.model.db.PhotoShootUpload;
import com.photo.shoot.model.db.enums.Status;
import com.photo.shoot.repository.LookBookRepository;
import com.photo.shoot.repository.UploadRepository;
import com.photo.shoot.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.util.StringUtils.getFilenameExtension;

/**
 * Service implementation for managing PhotoShootUpload entities.
 * Implements methods for uploading, updating, retrieving, and deleting photo uploads.
 */
@Service
public class UploadServiceImpl implements UploadService {
    private static final Logger logger = Logger.getLogger(UploadServiceImpl.class.getName());

    @Value("${picture.directory}")
    private String PICTURE_ROOT_DIRECTORY; // Root directory for storing uploaded pictures

    @Autowired
    private UploadRepository uploadRepository; // Repository for managing uploads
    @Autowired
    private LookBookRepository lookBookRepository; // Repository for managing look books

    /**
     * Uploads a picture file associated with a specific LookBook.
     *
     * @param lookBookId ID of the LookBook associated with the upload
     * @param file       MultipartFile representing the uploaded file
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If upload fails
     */
    @Override
    public ResponseEntity<?> uploadPicture(String lookBookId, MultipartFile file) {
        verifyLookBook(lookBookId);

        // Generate unique file name based on timestamp and original file extension
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String extension = getFilenameExtension(file.getOriginalFilename());
        String fileName = lookBookId + "_" + timestamp + "." + extension;
        logger.info("File name: " + fileName);

        // Construct file path and copy uploaded file to the specified directory
        File file1 = new File(fileName);
        Path filePath = Paths.get(PICTURE_ROOT_DIRECTORY, fileName);
        logger.info("Root dir: " + PICTURE_ROOT_DIRECTORY);
        logger.info("File path: " + filePath);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RequestValidationException("Error: " + ex.getMessage());
        }

        // Create PhotoShootUpload entity and save to repository
        PhotoShootUpload upload = new PhotoShootUpload();
        upload.setLookBookId(lookBookId);
        upload.setUrl(file1.getAbsolutePath());
        upload.setFileName(fileName);
        upload.setMimeType(URLConnection.guessContentTypeFromName(fileName));
        upload.setStatus(Status.ACTIVE);

        try {
            upload = uploadRepository.save(upload);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to create Picture Upload. " + e);
        }

        // Prepare response with created upload information
        ApiResponse<PhotoShootUpload> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Picture Upload created successfully");
        List<PhotoShootUpload> uploads = new ArrayList<>();
        uploads.add(upload);
        successfulResponse.setData(uploads);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Updates an existing picture upload with a new file.
     *
     * @param uploadId ID of the upload to update
     * @param file     MultipartFile representing the new file
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If update fails
     */
    @Override
    public ResponseEntity<?> updatePicture(String uploadId, MultipartFile file) {
        PhotoShootUpload existingUpload = verifyUpload(uploadId);

        // Generate unique file name based on timestamp and original file extension
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String extension = getFilenameExtension(file.getOriginalFilename());
        String fileName = existingUpload.getLookBookId() + "_" + timestamp + "." + extension;
        File file1 = new File(fileName);
        Path filePath = Paths.get(PICTURE_ROOT_DIRECTORY, fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RequestValidationException("Error: " + ex.getMessage());
        }

        // Update existing upload with new file details and save to repository
        existingUpload.setUrl(file1.getAbsolutePath());
        existingUpload.setFileName(fileName);
        existingUpload.setMimeType(URLConnection.guessContentTypeFromName(fileName));

        try {
            existingUpload = uploadRepository.save(existingUpload);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to update Picture Upload. " + e);
        }

        // Prepare response with updated upload information
        ApiResponse<PhotoShootUpload> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Picture Upload updated successfully");
        List<PhotoShootUpload> uploads = new ArrayList<>();
        uploads.add(existingUpload);
        successfulResponse.setData(uploads);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a picture upload by its ID.
     *
     * @param uploadId ID of the upload to retrieve
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved upload
     */
    @Override
    public ResponseEntity<?> getUpload(String uploadId) {
        PhotoShootUpload upload = verifyUpload(uploadId);
        ApiResponse<PhotoShootUpload> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Upload successfully");
        List<PhotoShootUpload> uploads = new ArrayList<>();
        uploads.add(upload);
        response.setData(uploads);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Retrieves all uploads associated with a given LookBook.
     *
     * @param lookBookId ID of the LookBook to retrieve uploads for
     * @return ResponseEntity containing ApiResponse with status, message, and list of uploads
     */
    @Override
    public ResponseEntity<?> getUploadByLookBook(String lookBookId) {
        verifyLookBook(lookBookId);
        List<PhotoShootUpload> uploadList = uploadRepository.
                findAllByLookBookIdAndStatus(lookBookId, Status.ACTIVE);
        ApiResponse<PhotoShootUpload> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Uploads list by Look Book id successfully");
        List<PhotoShootUpload> uploads = new ArrayList<>(uploadList);
        response.setData(uploads);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Deletes a picture upload by its ID.
     *
     * @param uploadId ID of the upload to delete
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws NotFoundException If upload with specified ID is not found
     */
    @Override
    public ResponseEntity<?> deleteUpload(String uploadId) {
        try {
            PhotoShootUpload upload = verifyUpload(uploadId);
            ApiResponse<?> apiResponse = new ApiResponse<>();
            apiResponse.setCode(HttpStatus.OK.value());
            apiResponse.setMessage("Upload successfully deleted");
            uploadRepository.deleteById(uploadId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Retrieves all Upload entry.
     *
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved PhotoShootUpload
     */
    @Override
    public ResponseEntity<?> getAllUpload() {
        List<PhotoShootUpload> uploads = (List<PhotoShootUpload>) uploadRepository.findAll();
        ApiResponse<PhotoShootUpload> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch All Uploads successfully");
        response.setData(uploads);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Verifies if a LookBook with the specified ID exists and is active.
     *
     * @param lookBookId ID of the LookBook to verify
     * @throws NotFoundException If LookBook with specified ID is not found or not active
     */
    private void verifyLookBook(String lookBookId) {
        Optional<LookBook> optionalLookBook = lookBookRepository.findByIdAndStatus(lookBookId, Status.ACTIVE);
        if (optionalLookBook.isEmpty()) {
            throw new NotFoundException("No Entry Found for this Look Book Id: " + lookBookId);
        }
    }

    /**
     * Verifies if an upload with the specified ID exists and is active.
     *
     * @param uploadId ID of the upload to verify
     * @return PhotoShootUpload entity if found
     * @throws NotFoundException If upload with specified ID is not found or not active
     */
    private PhotoShootUpload verifyUpload(String uploadId) {
        Optional<PhotoShootUpload> optionalPhotoShootUpload = uploadRepository.
                findByIdAndStatus(uploadId, Status.ACTIVE);
        if (optionalPhotoShootUpload.isEmpty()) {
            throw new NotFoundException("No Entry Found for this Id: " + uploadId);
        }
        return optionalPhotoShootUpload.get();
    }
}