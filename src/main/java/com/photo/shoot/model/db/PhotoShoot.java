package com.photo.shoot.model.db; // Package declaration for the model class

import com.photo.shoot.model.db.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity // Declaring this class as an entity
@Table(name = "PHOTO_SHOOT") // Mapping this entity to the PHOTO_SHOOT table
public class PhotoShoot implements Serializable { // Implementation of Serializable interface

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private String id; // Primary key ID

    @Column(name = "TITLE", nullable = false)
    private String title; // Title field

    @Column(name = "DESCRIPTION", nullable = false)
    private String description; // Description field

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "PhotoShoot{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdOn=" + createdOn +
                ", lastUpdatedOn=" + lastUpdatedOn +
                '}';
    }
}