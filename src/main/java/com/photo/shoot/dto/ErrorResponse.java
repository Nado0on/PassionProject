package com.photo.shoot.dto; // Package declaration for the DTO class

public class ErrorResponse { // Declaration of the ErrorResponse class

    private String message; // Private field for storing the error message

    // Constructor to initialize the message field
    public ErrorResponse(String message) {
        this.message = message;
    }

    // Getter method for message field
    public String getMessage() {
        return message;
    }

    // Setter method for message field
    public void setMessage(String message) {
        this.message = message;
    }
}