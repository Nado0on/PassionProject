package com.photo.shoot.service.impl;

import com.photo.shoot.dto.ApiResponse;
import com.photo.shoot.exception.NotFoundException;
import com.photo.shoot.exception.RequestValidationException;
import com.photo.shoot.model.db.PhotoShoot;
import com.photo.shoot.model.db.PhotoShootSchedule;
import com.photo.shoot.model.db.enums.Status;
import com.photo.shoot.repository.PhotoShootRepository;
import com.photo.shoot.repository.ScheduleRepository;
import com.photo.shoot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service implementation for managing PhotoShootSchedule entities.
 * Implements methods for creating, updating, retrieving, and deleting schedules.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger logger = Logger.getLogger(ScheduleServiceImpl.class.getName());

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PhotoShootRepository photoShootRepository;

    /**
     * Creates a new schedule entry for a given photo shoot.
     *
     * @param schedule The PhotoShootSchedule entity to create
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If creation fails
     */
    @Override
    public ResponseEntity<?> createSchedule(PhotoShootSchedule schedule) {
        verifyPhotoShoot(schedule.getPhotoShootId());
        try {
            schedule = scheduleRepository.save(schedule);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to create Schedule. " + e.getMessage());
        }
        ApiResponse<PhotoShootSchedule> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Schedule created successfully");
        List<PhotoShootSchedule> schedules = new ArrayList<>();
        schedules.add(schedule);
        successfulResponse.setData(schedules);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Updates an existing schedule entry.
     *
     * @param scheduleId ID of the schedule to update
     * @param schedule   Updated PhotoShootSchedule entity
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If update fails
     */
    @Override
    public ResponseEntity<?> updateSchedule(String scheduleId, PhotoShootSchedule schedule) {
        PhotoShootSchedule existingSchedule = verifySchedule(scheduleId);
        verifyPhotoShoot(schedule.getPhotoShootId());
        existingSchedule.setStartDate(schedule.getStartDate());
        existingSchedule.setEndDate(schedule.getEndDate());
        existingSchedule.setPhotoShootId(schedule.getPhotoShootId());
        existingSchedule.setStatus(schedule.getStatus());
        try {
            existingSchedule = scheduleRepository.save(existingSchedule);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to update Schedule. " + e.getMessage());
        }
        ApiResponse<PhotoShootSchedule> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Schedule updated successfully");
        List<PhotoShootSchedule> photoShootSchedules = new ArrayList<>();
        photoShootSchedules.add(existingSchedule);
        successfulResponse.setData(photoShootSchedules);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a schedule entry by its ID.
     *
     * @param scheduleId ID of the schedule to retrieve
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved schedule
     */
    @Override
    public ResponseEntity<?> getSchedule(String scheduleId) {
        PhotoShootSchedule schedule = verifySchedule(scheduleId);
        ApiResponse<PhotoShootSchedule> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Schedule successfully");
        List<PhotoShootSchedule> schedules = new ArrayList<>();
        schedules.add(schedule);
        response.setData(schedules);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Retrieves all schedules associated with a given photo shoot.
     *
     * @param photoShootId ID of the photo shoot to retrieve schedules for
     * @return ResponseEntity containing ApiResponse with status, message, and list of schedules
     */
    @Override
    public ResponseEntity<?> getScheduleByPhotoShootId(String photoShootId) {
        verifyPhotoShoot(photoShootId);
        List<PhotoShootSchedule> scheduleList = scheduleRepository.
                findAllByPhotoShootIdAndStatus(photoShootId, Status.ACTIVE);
        ApiResponse<PhotoShootSchedule> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Schedule list by Photo Shoot id successfully");
        List<PhotoShootSchedule> schedules = new ArrayList<>(scheduleList);
        response.setData(schedules);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Deletes a schedule entry by its ID.
     *
     * @param scheduleId ID of the schedule to delete
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws NotFoundException If schedule with specified ID is not found
     */
    @Override
    public ResponseEntity<?> deleteSchedule(String scheduleId) {
        try {
            PhotoShootSchedule schedule = verifySchedule(scheduleId);
            ApiResponse<?> apiResponse = new ApiResponse<>();
            apiResponse.setCode(HttpStatus.OK.value());
            apiResponse.setMessage("Schedule successfully deleted");
            scheduleRepository.deleteById(scheduleId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Retrieves all Schedule entry.
     *
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved PhotoShootSchedule
     */
    @Override
    public ResponseEntity<?> getAllSchedule() {
        List<PhotoShootSchedule> schedules = (List<PhotoShootSchedule>) scheduleRepository.findAll();
        ApiResponse<PhotoShootSchedule> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch All Schedule successfully");
        response.setData(schedules);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Verifies if a photo shoot with the specified ID exists and is active.
     *
     * @param photoShoot ID of the photo shoot to verify
     * @throws NotFoundException If photo shoot with specified ID is not found or not active
     */
    private void verifyPhotoShoot(String photoShoot) {
        Optional<PhotoShoot> optionalPhotoShoot = photoShootRepository.findByIdAndStatus(photoShoot, Status.ACTIVE);
        if (optionalPhotoShoot.isEmpty()) {
            throw new NotFoundException("No Entry Found for this Photo Shoot Id: " + photoShoot);
        }
    }

    /**
     * Verifies if a schedule with the specified ID exists and is active.
     *
     * @param scheduleId ID of the schedule to verify
     * @return PhotoShootSchedule entity if found
     * @throws NotFoundException If schedule with specified ID is not found or not active
     */
    private PhotoShootSchedule verifySchedule(String scheduleId) {
        Optional<PhotoShootSchedule> optionalPhotoShootSchedule = scheduleRepository.
                findByIdAndStatus(scheduleId, Status.ACTIVE);
        if (optionalPhotoShootSchedule.isEmpty()) {
            throw new NotFoundException("No Entry Found for this Id: " + scheduleId);
        }
        return optionalPhotoShootSchedule.get();
    }
}