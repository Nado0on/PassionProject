package com.photo.shoot.service;

import com.photo.shoot.model.db.PhotoShootSchedule;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    ResponseEntity<?> createSchedule(PhotoShootSchedule schedule);

    ResponseEntity<?> updateSchedule(String scheduleId, PhotoShootSchedule schedule);

    ResponseEntity<?> getSchedule(String scheduleId);

    ResponseEntity<?> getScheduleByPhotoShootId(String photoShootId);

    ResponseEntity<?> deleteSchedule(String scheduleId);

    ResponseEntity<?> getAllSchedule();
}