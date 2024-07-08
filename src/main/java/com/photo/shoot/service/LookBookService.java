package com.photo.shoot.service;

import com.photo.shoot.model.db.LookBook;
import org.springframework.http.ResponseEntity;

public interface LookBookService {
    ResponseEntity<?> createLookBook(LookBook lookBook);

    ResponseEntity<?> updateLookBook(String lookBookId, LookBook lookBook);

    ResponseEntity<?> getLookBook(String lookBookId);

    ResponseEntity<?> deleteLookBook(String lookBookId);

    ResponseEntity<?> getLookBookByPhotoShootId(String photoShootId);

    ResponseEntity<?> getAllLookBook();
}