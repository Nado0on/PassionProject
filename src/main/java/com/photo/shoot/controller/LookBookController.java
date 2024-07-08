package com.photo.shoot.controller; // Package declaration for the controller class

import com.photo.shoot.model.db.LookBook;
import com.photo.shoot.service.LookBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Declaring this class as a REST controller
public class LookBookController {
    @Autowired // Autowiring the LookBookService dependency
    private LookBookService lookBookService; // Declaring the LookBookService field

    /**
     * Handles POST requests to create a new LookBook.
     *
     * @param lookBook The LookBook object to be created (validated and bound from request body)
     * @return ResponseEntity containing the response from LookBookService
     */
    @RequestMapping(value = "/look_book", method = RequestMethod.POST)
    public ResponseEntity<?> createLookBook(@Valid @RequestBody LookBook lookBook) {
        return lookBookService.createLookBook(lookBook); // Calling the createLookBook method in LookBookService and returning the response
    }

    /**
     * Handles PUT requests to update an existing LookBook.
     *
     * @param lookBookId The ID of the LookBook to update (from request parameter)
     * @param lookBook   The updated LookBook object (validated and bound from request body)
     * @return ResponseEntity containing the response from LookBookService
     */
    @RequestMapping(value = "/look_book", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLookBook(@RequestParam(value = "look_book_id") String lookBookId,
                                            @Valid @RequestBody LookBook lookBook) {
        return lookBookService.updateLookBook(lookBookId, lookBook); // Calling the updateLookBook method in LookBookService and returning the response
    }

    /**
     * Handles GET requests to fetch a LookBook by its ID.
     *
     * @param lookBookId The ID of the LookBook to fetch (from request parameter)
     * @return ResponseEntity containing the response from LookBookService
     */
    @RequestMapping(value = "/look_book", method = RequestMethod.GET)
    public ResponseEntity<?> getLookBook(@RequestParam(value = "look_book_id") String lookBookId) {
        return lookBookService.getLookBook(lookBookId); // Calling the getLookBook method in LookBookService and returning the response
    }

    /**
     * Handles GET requests to fetch all LookBook.
     *
     * @return ResponseEntity containing the response from LookBookService
     */
    @RequestMapping(value = "/look_book/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLookBook() {
        return lookBookService.getAllLookBook();
    }

    /**
     * Handles GET requests to fetch a LookBook by its associated PhotoShoot ID.
     *
     * @param photoShootId The ID of the PhotoShoot to fetch LookBooks for (from request parameter)
     * @return ResponseEntity containing the response from LookBookService
     */
    @RequestMapping(value = "/look_book/id", method = RequestMethod.GET)
    public ResponseEntity<?> getLookBookByPhotoShootId(@RequestParam(value = "photo_shoot_id") String photoShootId) {
        return lookBookService.getLookBookByPhotoShootId(photoShootId); // Calling the getLookBookByPhotoShootId method in LookBookService and returning the response
    }

    /**
     * Handles DELETE requests to delete a LookBook by its ID.
     *
     * @param lookBookId The ID of the LookBook to delete (from request parameter)
     * @return ResponseEntity containing the response from LookBookService
     */
    @RequestMapping(value = "/look_book", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLookBook(@RequestParam(value = "look_book_id") String lookBookId) {
        return lookBookService.deleteLookBook(lookBookId); // Calling the deleteLookBook method in LookBookService and returning the response
    }
}