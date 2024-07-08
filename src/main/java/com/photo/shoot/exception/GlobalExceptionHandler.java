package com.photo.shoot.exception; // Package declaration for the exception handling classes

import com.photo.shoot.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // Declaring this class as a global exception handler
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handler for all generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()); // Creating an ErrorResponse with the exception message
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // Returning a ResponseEntity with INTERNAL_SERVER_ERROR status
    }

    // Handler for custom RequestValidationException
    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorResponse> handleRequestValidationException(RequestValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()); // Creating an ErrorResponse with the exception message
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Returning a ResponseEntity with BAD_REQUEST status
    }

    // Handler for custom NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()); // Creating an ErrorResponse with the exception message
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // Returning a ResponseEntity with NOT_FOUND status
    }
}