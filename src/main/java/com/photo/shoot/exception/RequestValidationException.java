package com.photo.shoot.exception; // Package declaration for the exception classes

public class RequestValidationException extends RuntimeException { // Declaration of the RequestValidationException class extending RuntimeException
    public RequestValidationException(String message) { // Constructor accepting a message parameter
        super(message); // Passing the message to the superclass constructor
    }
}