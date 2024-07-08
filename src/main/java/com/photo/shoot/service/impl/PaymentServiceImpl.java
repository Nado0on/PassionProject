package com.photo.shoot.service.impl;

import com.photo.shoot.dto.ApiResponse;
import com.photo.shoot.exception.NotFoundException;
import com.photo.shoot.exception.RequestValidationException;
import com.photo.shoot.model.db.PhotoShoot;
import com.photo.shoot.model.db.PhotoShootPayment;
import com.photo.shoot.model.db.enums.Status;
import com.photo.shoot.repository.PaymentRepository;
import com.photo.shoot.repository.PhotoShootRepository;
import com.photo.shoot.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service implementation for managing PhotoShootPayment entities.
 * Implements methods for creating, updating, retrieving, and deleting payments.
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PhotoShootRepository photoShootRepository;

    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    /**
     * Creates a new payment entry.
     *
     * @param payment The Payment entity to create
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If creation fails
     */
    @Override
    public ResponseEntity<?> createPayment(PhotoShootPayment payment) {
        verifyPhotoShoot(payment.getPhotoShootId());
        try {
            payment = paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to create Payment. " + e.getMessage());
        }
        ApiResponse<PhotoShootPayment> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Payment created successfully");
        List<PhotoShootPayment> photoShootPayments = new ArrayList<>();
        photoShootPayments.add(payment);
        successfulResponse.setData(photoShootPayments);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Updates an existing payment entry.
     *
     * @param paymentId ID of the Payment to update
     * @param payment   Updated Payment entity
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws RequestValidationException If update fails
     */
    @Override
    public ResponseEntity<?> updatePayment(String paymentId, PhotoShootPayment payment) {
        PhotoShootPayment existingPayment = verifyPayment(paymentId);
        verifyPhotoShoot(payment.getPhotoShootId());
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setStatus(payment.getStatus());
        existingPayment.setPhotoShootId(payment.getPhotoShootId());
        try {
            existingPayment = paymentRepository.save(existingPayment);
        } catch (Exception e) {
            throw new RequestValidationException("Failed to update Payment. " + e.getMessage());
        }
        ApiResponse<PhotoShootPayment> successfulResponse = new ApiResponse<>();
        successfulResponse.setCode(HttpStatus.CREATED.value());
        successfulResponse.setMessage("Payment updated successfully");
        List<PhotoShootPayment> photoShootPayments = new ArrayList<>();
        photoShootPayments.add(existingPayment);
        successfulResponse.setData(photoShootPayments);
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a payment entry by its ID.
     *
     * @param paymentId ID of the Payment to retrieve
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved Payment
     */
    @Override
    public ResponseEntity<?> getPayment(String paymentId) {
        PhotoShootPayment payment = verifyPayment(paymentId);
        ApiResponse<PhotoShootPayment> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Payment successfully");
        List<PhotoShootPayment> payments = new ArrayList<>();
        payments.add(payment);
        response.setData(payments);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Retrieves a list of payment entries associated with a specific PhotoShoot ID.
     *
     * @param photoShootId ID of the PhotoShoot
     * @return ResponseEntity containing ApiResponse with status, message, and list of Payments
     */
    @Override
    public ResponseEntity<?> getPaymentByPhotoShootId(String photoShootId) {
        verifyPhotoShoot(photoShootId);
        List<PhotoShootPayment> paymentList = paymentRepository.
                findAllByPhotoShootIdAndStatus(photoShootId, Status.ACTIVE);
        ApiResponse<PhotoShootPayment> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch Payment list by Photo Shoot id successfully");
        List<PhotoShootPayment> payments = new ArrayList<>(paymentList);
        response.setData(payments);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Deletes a payment entry by its ID.
     *
     * @param paymentId ID of the Payment to delete
     * @return ResponseEntity containing ApiResponse with status and message
     * @throws NotFoundException If Payment with specified ID is not found
     */
    @Override
    public ResponseEntity<?> deletePayment(String paymentId) {
        try {
            PhotoShootPayment payment = verifyPayment(paymentId);
            ApiResponse<?> apiResponse = new ApiResponse<>();
            apiResponse.setCode(HttpStatus.OK.value());
            apiResponse.setMessage("Payment successfully deleted");
            paymentRepository.deleteById(paymentId);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    /**
     * Retrieves all Payment entry.
     *
     * @return ResponseEntity containing ApiResponse with status, message, and retrieved PhotoShootPayment
     */
    @Override
    public ResponseEntity<?> getAllPayment() {
        List<PhotoShootPayment> payments = (List<PhotoShootPayment>) paymentRepository.findAll();
        ApiResponse<PhotoShootPayment> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Fetch All Payments successfully");
        response.setData(payments);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
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

    /**
     * Verifies if a Payment with the specified ID exists and is active.
     *
     * @param paymentId ID of the Payment to verify
     * @return PhotoShootPayment entity if found
     * @throws NotFoundException If Payment with specified ID is not found or not active
     */
    private PhotoShootPayment verifyPayment(String paymentId) {
        Optional<PhotoShootPayment> optionalPhotoShootPayment = paymentRepository.
                findByIdAndStatus(paymentId, Status.ACTIVE);
        if (optionalPhotoShootPayment.isEmpty()) {
            throw new NotFoundException("No Entry Found for this Id: " + paymentId);
        }
        return optionalPhotoShootPayment.get();
    }
}