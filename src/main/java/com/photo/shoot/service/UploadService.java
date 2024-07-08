package com.photo.shoot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseEntity<?> uploadPicture(String lookBookId, MultipartFile file);

    ResponseEntity<?> updatePicture(String uploadId, MultipartFile file);

    ResponseEntity<?> getUpload(String uploadId);

    ResponseEntity<?> getUploadByLookBook(String lookBookId);

    ResponseEntity<?> deleteUpload(String uploadId);

    ResponseEntity<?> getAllUpload();
}