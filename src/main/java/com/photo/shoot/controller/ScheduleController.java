package com.photo.shoot.controller; // Package declaration for the controller class

import com.photo.shoot.model.db.PhotoShootSchedule;
import com.photo.shoot.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController // Declaring this class as a REST controller
public class ScheduleController {
    private static final Logger logger = Logger.getLogger(ScheduleController.class.getName()); // Creating a Logger instance

    @Autowired // Autowiring the ScheduleService dependency
    private ScheduleService scheduleService; // Declaring the ScheduleService field

    /**
     * Handles POST requests to create a new schedule for a photo shoot.
     *
     * @param schedule The PhotoShootSchedule object to be created (validated and bound from request body)
     * @return ResponseEntity containing the response from ScheduleService
     */
    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    public ResponseEntity<?> createSchedule(@Valid @RequestBody PhotoShootSchedule schedule) {
        logger.info("Call The Add Schedule Api."); // Logging the API call
        logger.info("The Request Body are: " + schedule.toString()); // Logging the request body
        return scheduleService.createSchedule(schedule); // Calling the createSchedule method in ScheduleService and returning the response
    }

    /**
     * Handles PUT requests to update an existing schedule for a photo shoot.
     *
     * @param scheduleId The ID of the schedule to update (from request parameter)
     * @param schedule   The updated PhotoShootSchedule object (validated and bound from request body)
     * @return ResponseEntity containing the response from ScheduleService
     */
    @RequestMapping(value = "/schedule", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSchedule(@RequestParam(value = "schedule_id") String scheduleId,
                                            @Valid @RequestBody PhotoShootSchedule schedule) {
        logger.info("Call The Update Schedule Api."); // Logging the API call
        logger.info("The Request Body are: " + schedule.toString()); // Logging the request body
        return scheduleService.updateSchedule(scheduleId, schedule); // Calling the updateSchedule method in ScheduleService and returning the response
    }

    /**
     * Handles GET requests to fetch a schedule by its ID.
     *
     * @param scheduleId The ID of the schedule to fetch (from request parameter)
     * @return ResponseEntity containing the response from ScheduleService
     */
    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ResponseEntity<?> getSchedule(@RequestParam(value = "schedule_id") String scheduleId) {
        logger.info("Call The Get Schedule Api."); // Logging the API call
        return scheduleService.getSchedule(scheduleId); // Calling the getSchedule method in ScheduleService and returning the response
    }

    /**
     * Handles GET requests to fetch schedules by a photo shoot ID.
     *
     * @param photoShootId The ID of the photo shoot to fetch schedules for (from request parameter)
     * @return ResponseEntity containing the response from ScheduleService
     */
    @RequestMapping(value = "/schedule/id", method = RequestMethod.GET)
    public ResponseEntity<?> getScheduleByPhotoShootId(@RequestParam(value = "photo_shoot_id") String photoShootId) {
        logger.info("Call The Get Schedule by Photo Shoot Id Api."); // Logging the API call
        return scheduleService.getScheduleByPhotoShootId(photoShootId); // Calling the getScheduleByPhotoShootId method in ScheduleService and returning the response
    }

    /**
     * Handles DELETE requests to delete a schedule by its ID.
     *
     * @param scheduleId The ID of the schedule to delete (from request parameter)
     * @return ResponseEntity containing the response from ScheduleService
     */
    @RequestMapping(value = "/schedule", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSchedule(@RequestParam(value = "schedule_id") String scheduleId) {
        logger.info("Call The Delete Schedule Api."); // Logging the API call
        return scheduleService.deleteSchedule(scheduleId); // Calling the deleteSchedule method in ScheduleService and returning the response
    }

    /**
     * Handles GET requests to fetch all Schedule.
     *
     * @return ResponseEntity containing the response from ScheduleService
     */
    @RequestMapping(value = "/schedule/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllSchedule() {
        return scheduleService.getAllSchedule();
    }
}