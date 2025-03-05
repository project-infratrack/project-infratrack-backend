package com.G153.InfratrackUserPortal.DTO;

public class ProblemReportDTO {
    private String reportType;
    private String description;
    private String location;
    private byte[] image; // Change to byte array

    // Getters
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

    // Setters
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
}