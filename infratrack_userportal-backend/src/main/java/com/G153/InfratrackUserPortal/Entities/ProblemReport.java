package com.G153.InfratrackUserPortal.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "reports")
public class ProblemReport {
    @Id
    private String id;
    private String userId;
    private String reportType;
    private String description;
    private String location;
    private byte[] image;
    private String status;
    private double latitude;
    private double longitude;
    private String priorityLevel = "Pending";
    private int thumbsUp;
    private int thumbsDown;
    private Set<String> thumbsUpUsers = new HashSet<>();
    private Set<String> thumbsDownUsers = new HashSet<>();

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public Set<String> getThumbsUpUsers() {
        return thumbsUpUsers;
    }

    public void setThumbsUpUsers(Set<String> thumbsUpUsers) {
        this.thumbsUpUsers = thumbsUpUsers;
    }

    public Set<String> getThumbsDownUsers() {
        return thumbsDownUsers;
    }

    public void setThumbsDownUsers(Set<String> thumbsDownUsers) {
        this.thumbsDownUsers = thumbsDownUsers;
    }
}