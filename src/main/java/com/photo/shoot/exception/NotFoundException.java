package com.photo.shoot.exception; // Package declaration for the exception classes

public class NotFoundException extends RuntimeException { // Declaration of the NotFoundException class extending RuntimeException
    public NotFoundException(String message) { // Constructor accepting a message parameter
        super(message); // Passing the message to the superclass constructor
    }
}