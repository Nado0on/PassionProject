package com.photo.shoot.dto; // Package declaration for the DTO class

import java.util.List;

public class ApiResponse<T> { // Declaration of the generic ApiResponse class

    private int code; // Private field for storing the response code
    private String message; // Private field for storing the response message
    private List<T> data; // Private field for storing the response data as a list of generic type T

    // Constructor to initialize all fields
    public ApiResponse(int code, String message, List<T> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Constructor to initialize code and data fields
    public ApiResponse(int code, List<T> data) {
        this.code = code;
        this.data = data;
    }

    // Default constructor
    public ApiResponse() {
    }

    // Constructor to initialize code and message fields
    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter method for code field
    public int getCode() {
        return code;
    }

    // Setter method for code field
    public void setCode(int code) {
        this.code = code;
    }

    // Getter method for message field
    public String getMessage() {
        return message;
    }

    // Setter method for message field
    public void setMessage(String message) {
        this.message = message;
    }

    // Getter method for data field
    public List<T> getData() {
        return data;
    }

    // Setter method for data field
    public void setData(List<T> data) {
        this.data = data;
    }
}