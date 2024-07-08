package com.photo.shoot.model.db; // Package declaration for the model class

import com.photo.shoot.model.db.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity // Declaring this class as an entity
@Table(name = "LOOK_BOOK") // Mapping this entity to the LOOK_BOOK table
public class LookBook implements Serializable { // Implementation of Serializable interface

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private String id; // Primary key ID

    @Column(name = "AUTHOR1", nullable = false)
    private String author1; // Author1 field

    @Column(name = "AUTHOR2", nullable = false)
    private String author2; // Author2 field

    @Column(name = "PHOTO_SHOOT_ID", nullable = false)
    private String photoShootId; // Photo shoot ID field

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

    public String getAuthor1() {
        return author1;
    }

    public void setAuthor1(String author1) {
        this.author1 = author1;
    }

    public String getAuthor2() {
        return author2;
    }

    public void setAuthor2(String author2) {
        this.author2 = author2;
    }

    public String getPhotoShootId() {
        return photoShootId;
    }

    public void setPhotoShootId(String photoShootId) {
        this.photoShootId = photoShootId;
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
        return "LookBook{" +
                "id='" + id + '\'' +
                ", author1='" + author1 + '\'' +
                ", author2='" + author2 + '\'' +
                ", photoShootId='" + photoShootId + '\'' +
                ", status=" + status +
                ", createdOn=" + createdOn +
                ", lastUpdatedOn=" + lastUpdatedOn +
                '}';
    }
}