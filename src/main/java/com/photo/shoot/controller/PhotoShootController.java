package com.photo.shoot.controller; // Package declaration for the controller class

import com.photo.shoot.model.db.PhotoShoot;
import com.photo.shoot.service.PhotoShootService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController // Declaring this class as a REST controller
public class PhotoShootController {
    private static final Logger logger = Logger.getLogger(PhotoShootController.class.getName()); // Creating a Logger instance

    @Autowired // Autowiring the PhotoShootService dependency
    private PhotoShootService photoShootService; // Declaring the PhotoShootService field

    /**
     * Handles POST requests to create a new Photo Shoot.
     *
     * @param photoShoot The PhotoShoot object to be created (validated and bound from request body)
     * @return ResponseEntity containing the response from PhotoShootService
     */
    @RequestMapping(value = "/photo_shoot", method = RequestMethod.POST)
    public ResponseEntity<?> createPhotoShoot(@Valid @RequestBody PhotoShoot photoShoot) {
        logger.info("Call The Add Photo Shoot Api."); // Logging the API call
        logger.info("The Request Body are: " + photoShoot.toString()); // Logging the request body
        return photoShootService.createPhotoShoot(photoShoot); // Calling the createPhotoShoot method in PhotoShootService and returning the response
    }

    /**
     * Handles PUT requests to update an existing Photo Shoot.
     *
     * @param photoShootId The ID of the Photo Shoot to update (from request parameter)
     * @param photoShoot   The updated PhotoShoot object (validated and bound from request body)
     * @return ResponseEntity containing the response from PhotoShootService
     */
    @RequestMapping(value = "/photo_shoot", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePhotoShoot(@RequestParam(value = "photo_shoot_id") String photoShootId,
                                              @Valid @RequestBody PhotoShoot photoShoot) {
        logger.info("Call The Update Photo Shoot Api."); // Logging the API call
        logger.info("The Request Body are: " + photoShoot.toString()); // Logging the request body
        return photoShootService.updatePhotoShoot(photoShootId, photoShoot); // Calling the updatePhotoShoot method in PhotoShootService and returning the response
    }

    /**
     * Handles GET requests to fetch a Photo Shoot by its ID.
     *
     * @param photoShootId The ID of the Photo Shoot to fetch (from request parameter)
     * @return ResponseEntity containing the response from PhotoShootService
     */
    @RequestMapping(value = "/photo_shoot", method = RequestMethod.GET)
    public ResponseEntity<?> getPhotoShoot(@RequestParam(value = "photo_shoot_id") String photoShootId) {
        logger.info("Call The Get Photo Shoot Api."); // Logging the API call
        return photoShootService.getPhotoShoot(photoShootId); // Calling the getPhotoShoot method in PhotoShootService and returning the response
    }

    /**
     * Handles DELETE requests to delete a Photo Shoot by its ID.
     *
     * @param photoShootId The ID of the Photo Shoot to delete (from request parameter)
     * @return ResponseEntity containing the response from PhotoShootService
     */
    @RequestMapping(value = "/photo_shoot", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePhotoShoot(@RequestParam(value = "photo_shoot_id") String photoShootId) {
        logger.info("Call The Delete Photo Shoot Api."); // Logging the API call
        return photoShootService.deletePhotoShoot(photoShootId); // Calling the deletePhotoShoot method in PhotoShootService and returning the response
    }

    /**
     * Handles GET requests to fetch all Photo Shoot.
     *
     * @return ResponseEntity containing the response from PhotoShootService
     */
    @RequestMapping(value = "/photo_shoot/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPhotoShoot() {
        return photoShootService.getAllPhotoShoot();
    }
}