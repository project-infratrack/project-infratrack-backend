package com.G153.InfratrackUserPortal.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
public class ProblemReport {
    @Id
    private String id;
    private String reportType;
    private String description;
    private String location;
    private byte[] image; // Change to byte array
    private String status;

    private double latitude;
    private double longitude;


    // Getters and Setters

    public String getId() {
        return id;
    }

    public String getReportType() {
        return reportType;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public byte[] getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}