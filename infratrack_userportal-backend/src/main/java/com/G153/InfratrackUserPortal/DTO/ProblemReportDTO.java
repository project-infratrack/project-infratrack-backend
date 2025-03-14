package com.G153.InfratrackUserPortal.DTO;

import java.util.HashSet;
import java.util.Set;

public class ProblemReportDTO {
    private String userId;
    private String reportType;
    private String description;
    private String location;
    private byte[] image; // Change to byte array
    private double latitude;
    private double longitude;
    private String priorityLevel = "Pending";
    private int thumbsUp;
    private int thumbsDown;
    private Set<String> thumbsUpUsers = new HashSet<>();
    private Set<String> thumbsDownUsers = new HashSet<>();

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

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getUserId() {
        return userId;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public Set<String> getThumbsUpUsers() {
        return thumbsUpUsers;
    }

    public Set<String> getThumbsDownUsers() {
        return thumbsDownUsers;
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

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    public void setThumbsUpUsers(Set<String> thumbsUpUsers) {
        this.thumbsUpUsers = thumbsUpUsers;
    }

    public void setThumbsDownUsers(Set<String> thumbsDownUsers) {
        this.thumbsDownUsers = thumbsDownUsers;
    }
}