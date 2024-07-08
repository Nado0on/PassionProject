package com.photo.shoot.service;

import com.photo.shoot.model.db.PhotoShootPayment;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> createPayment(PhotoShootPayment payment);

    ResponseEntity<?> updatePayment(String paymentId, PhotoShootPayment payment);

    ResponseEntity<?> getPayment(String paymentId);

    ResponseEntity<?> getPaymentByPhotoShootId(String photoShootId);

    ResponseEntity<?> deletePayment(String paymentId);

    ResponseEntity<?> getAllPayment();
}