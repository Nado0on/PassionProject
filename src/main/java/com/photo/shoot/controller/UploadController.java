package com.photo.shoot.controller; // Package declaration for the controller class

import com.photo.shoot.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController // Declaring this class as a REST controller
public class UploadController {
    private static final Logger logger = Logger.getLogger(UploadController.class.getName()); // Creating a Logger instance

    @Autowired // Autowiring the UploadService dependency
    private UploadService uploadService; // Declaring the UploadService field

    /**
     * Handles PUT requests to upload a picture for a specific look book.
     *
     * @param lookBookId The ID of the look book to associate the upload with (from request parameter)
     * @param file       The file to be uploaded (from request parameter)
     * @return ResponseEntity containing the response from UploadService
     */
    @RequestMapping(value = "/upload", method = RequestMethod.PUT)
    public ResponseEntity<?> uploadPicture(@RequestParam(value = "look_book_id") String lookBookId,
                                           @RequestParam(value = "file") MultipartFile file) {
        logger.info("Call The Picture Upload Create Api."); // Logging the API call
        return uploadService.uploadPicture(lookBookId, file); // Calling the uploadPicture method in UploadService and returning the response
    }

    /**
     * Handles PUT requests to update an existing picture upload.
     *
     * @param uploadId The ID of the upload to update (from request parameter)
     * @param file     The updated file to be uploaded (from request parameter)
     * @return ResponseEntity containing the response from UploadService
     */
    @RequestMapping(value = "/upload/id", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePicture(@RequestParam(value = "upload_id") String uploadId,
                                           @RequestParam(value = "file") MultipartFile file) {
        logger.info("Call The Picture Upload Update Api."); // Logging the API call
        return uploadService.updatePicture(uploadId, file); // Calling the updatePicture method in UploadService and returning the response
    }

    /**
     * Handles GET requests to fetch a picture upload by its ID.
     *
     * @param uploadId The ID of the upload to fetch (from request parameter)
     * @return ResponseEntity containing the response from UploadService
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ResponseEntity<?> getUpload(@RequestParam(value = "upload_id") String uploadId) {
        logger.info("Call The Get Picture Upload Api."); // Logging the API call
        return uploadService.getUpload(uploadId); // Calling the getUpload method in UploadService and returning the response
    }

    /**
     * Handles GET requests to fetch picture uploads by look book ID.
     *
     * @param lookBookId The ID of the look book to fetch uploads for (from request parameter)
     * @return ResponseEntity containing the response from UploadService
     */
    @RequestMapping(value = "/upload/look_book", method = RequestMethod.GET)
    public ResponseEntity<?> getUploadByLookBook(@RequestParam(value = "look_book_id") String lookBookId) {
        logger.info("Call The Get Picture Upload by Look Book Api."); // Logging the API call
        return uploadService.getUploadByLookBook(lookBookId); // Calling the getUploadByLookBook method in UploadService and returning the response
    }

    /**
     * Handles DELETE requests to delete a picture upload by its ID.
     *
     * @param uploadId The ID of the upload to delete (from request parameter)
     * @return ResponseEntity containing the response from UploadService
     */
    @RequestMapping(value = "/upload", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUpload(@RequestParam(value = "upload_id") String uploadId) {
        logger.info("Call The Delete Picture Upload Api."); // Logging the API call
        return uploadService.deleteUpload(uploadId); // Calling the deleteUpload method in UploadService and returning the response
    }

    /**
     * Handles GET requests to fetch all Upload.
     *
     * @return ResponseEntity containing the response from UploadService
     */
    @RequestMapping(value = "/upload/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUpload() {
        return uploadService.getAllUpload();
    }
}