package com.photo.shoot.service;

import com.photo.shoot.model.db.PhotoShoot;
import org.springframework.http.ResponseEntity;

public interface PhotoShootService {
    ResponseEntity<?> createPhotoShoot(PhotoShoot photoShoot);

    ResponseEntity<?> updatePhotoShoot(String photoShootId, PhotoShoot photoShoot);

    ResponseEntity<?> getPhotoShoot(String photoShootId);

    ResponseEntity<?> deletePhotoShoot(String photoShootId);

    ResponseEntity<?> getAllPhotoShoot();
}