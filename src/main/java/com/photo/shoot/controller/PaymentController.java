package com.photo.shoot.controller; // Package declaration for the controller class

import com.photo.shoot.model.db.PhotoShootPayment;
import com.photo.shoot.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController // Declaring this class as a REST controller
public class PaymentController {
    private static final Logger logger = Logger.getLogger(PaymentController.class.getName()); // Creating a Logger instance

    @Autowired // Autowiring the PaymentService dependency
    private PaymentService paymentService; // Declaring the PaymentService field

    /**
     * Handles POST requests to create a new Payment.
     *
     * @param payment The PhotoShootPayment object to be created (validated and bound from request body)
     * @return ResponseEntity containing the response from PaymentService
     */
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ResponseEntity<?> createPayment(@Valid @RequestBody PhotoShootPayment payment) {
        logger.info("Call The Add Payment Api."); // Logging the API call
        logger.info("The Request Body are: " + payment.toString()); // Logging the request body
        return paymentService.createPayment(payment); // Calling the createPayment method in PaymentService and returning the response
    }

    /**
     * Handles PUT requests to update an existing Payment.
     *
     * @param paymentId The ID of the Payment to update (from request parameter)
     * @param payment   The updated PhotoShootPayment object (validated and bound from request body)
     * @return ResponseEntity containing the response from PaymentService
     */
    @RequestMapping(value = "/payment", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePayment(@RequestParam(value = "payment_id") String paymentId,
                                           @Valid @RequestBody PhotoShootPayment payment) {
        logger.info("Call The Update Payment Api."); // Logging the API call
        logger.info("The Request Body are: " + payment.toString()); // Logging the request body
        return paymentService.updatePayment(paymentId, payment); // Calling the updatePayment method in PaymentService and returning the response
    }

    /**
     * Handles GET requests to fetch a Payment by its ID.
     *
     * @param paymentId The ID of the Payment to fetch (from request parameter)
     * @return ResponseEntity containing the response from PaymentService
     */
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ResponseEntity<?> getPayment(@RequestParam(value = "payment_id") String paymentId) {
        logger.info("Call The Get Payment Api."); // Logging the API call
        return paymentService.getPayment(paymentId); // Calling the getPayment method in PaymentService and returning the response
    }

    /**
     * Handles GET requests to fetch Payments by associated PhotoShoot ID.
     *
     * @param photoShootId The ID of the PhotoShoot to fetch Payments for (from request parameter)
     * @return ResponseEntity containing the response from PaymentService
     */
    @RequestMapping(value = "/payment/id", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentByPhotoShootId(@RequestParam(value = "photo_shoot_id") String photoShootId) {
        logger.info("Call The Get Payment by Photo Shoot Id Api."); // Logging the API call
        return paymentService.getPaymentByPhotoShootId(photoShootId); // Calling the getPaymentByPhotoShootId method in PaymentService and returning the response
    }

    /**
     * Handles DELETE requests to delete a Payment by its ID.
     *
     * @param paymentId The ID of the Payment to delete (from request parameter)
     * @return ResponseEntity containing the response from PaymentService
     */
    @RequestMapping(value = "/payment", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePayment(@RequestParam(value = "payment_id") String paymentId) {
        logger.info("Call The Delete Payment Api."); // Logging the API call
        return paymentService.deletePayment(paymentId); // Calling the deletePayment method in PaymentService and returning the response
    }

    /**
     * Handles GET requests to fetch all Payment.
     *
     * @return ResponseEntity containing the response from PaymentService
     */
    @RequestMapping(value = "/payment/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPayment() {
        return paymentService.getAllPayment();
    }
}