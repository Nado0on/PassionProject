package com.photo.shoot.model.db; // Package declaration for the model class

import com.photo.shoot.model.db.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity // Declaring this class as an entity
@Table(name = "UPLOAD") // Mapping this entity to the UPLOAD table
public class PhotoShootUpload implements Serializable { // Implementation of Serializable interface

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private String id; // Primary key ID

    @Column(name = "LOOK_BOOK_ID", nullable = false)
    private String lookBookId; // Look book ID field

    @Column(name = "MIME_TYPE", nullable = false)
    private String mimeType; // MIME type field

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName; // File name field

    @Column(name = "URL", nullable = false)
    private String url; // URL field

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status; // Status field using the Status enum

    @CreationTimestamp
    @Column(name = "CREATED_ON", nullable = false, updatable = false)
    private ZonedDateTime createdOn; // Creation timestamp field

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private ZonedDateTime lastUpdatedOn; // Last updated timestamp field

    // Getters and setters for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLookBookId() {
        return lookBookId;
    }

    public void setLookBookId(String lookBookId) {
        this.lookBookId = lookBookId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    // toString method for debugging and logging

    @Override
    public String toString() {
        return "PhotoShootUpload{" +
                "id='" + id + '\'' +
                ", lookBookId='" + lookBookId + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", createdOn=" + createdOn +
                ", lastUpdatedOn=" + lastUpdatedOn +
                '}';
    }
}