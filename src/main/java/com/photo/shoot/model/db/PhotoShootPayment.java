package com.photo.shoot.model.db; // Package declaration for the model class

import com.photo.shoot.model.db.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity // Declaring this class as an entity
@Table(name = "PAYMENT") // Mapping this entity to the PAYMENT table
public class PhotoShootPayment implements Serializable { // Implementation of Serializable interface

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private String id; // Primary key ID

    @Column(name = "PHOTO_SHOOT_ID", nullable = false)
    private String photoShootId; // Photo shoot ID field

    @Column(name = "AMOUNT", nullable = false)
    private Double amount; // Amount field

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

    public String getPhotoShootId() {
        return photoShootId;
    }

    public void setPhotoShootId(String photoShootId) {
        this.photoShootId = photoShootId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
        return "PhotoShootPayment{" +
                "id='" + id + '\'' +
                ", photoShootId='" + photoShootId + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", createdOn=" + createdOn +
                ", lastUpdatedOn=" + lastUpdatedOn +
                '}';
    }
}