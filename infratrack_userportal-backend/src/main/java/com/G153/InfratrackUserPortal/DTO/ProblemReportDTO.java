package com.G153.InfratrackUserPortal.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object for problem reports.
 */
public class ProblemReportDTO {
    private String userId;
    private String reportType;
    private String description;
    private String location;
    private MultipartFile image;
    private double latitude;
    private double longitude;
    private String priorityLevel = "Pending";
    private int thumbsUp;
    private int thumbsDown;
    private Set<String> thumbsUpUsers = new HashSet<>();
    private Set<String> thumbsDownUsers = new HashSet<>();

    /**
     * Gets the report type.
     *
     * @return the report type
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    public MultipartFile getImage() {
        return image;
    }

    /**
     * Gets the latitude.
     *
     * @return the latitude
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * Gets the longitude.
     *
     * @return the longitude
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the priority level.
     *
     * @return the priority level
     */
    public String getPriorityLevel() {
        return priorityLevel;
    }

    /**
     * Gets the number of thumbs up.
     *
     * @return the number of thumbs up
     */
    public int getThumbsUp() {
        return thumbsUp;
    }

    /**
     * Gets the number of thumbs down.
     *
     * @return the number of thumbs down
     */
    public int getThumbsDown() {
        return thumbsDown;
    }

    /**
     * Gets the set of users who gave thumbs up.
     *
     * @return the set of users who gave thumbs up
     */
    public Set<String> getThumbsUpUsers() {
        return thumbsUpUsers;
    }

    /**
     * Gets the set of users who gave thumbs down.
     *
     * @return the set of users who gave thumbs down
     */
    public Set<String> getThumbsDownUsers() {
        return thumbsDownUsers;
    }

    /**
     * Sets the report type.
     *
     * @param reportType the report type
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Sets the description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the image.
     *
     * @param image the image
     */
    public void setImage(MultipartFile image) {
        this.image = image;
    }

    /**
     * Sets the longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Sets the latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Sets the priority level.
     *
     * @param priorityLevel the priority level
     */
    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    /**
     * Sets the number of thumbs up.
     *
     * @param thumbsUp the number of thumbs up
     */
    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    /**
     * Sets the number of thumbs down.
     *
     * @param thumbsDown the number of thumbs down
     */
    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    /**
     * Sets the set of users who gave thumbs up.
     *
     * @param thumbsUpUsers the set of users who gave thumbs up
     */
    public void setThumbsUpUsers(Set<String> thumbsUpUsers) {
        this.thumbsUpUsers = thumbsUpUsers;
    }

    /**
     * Sets the set of users who gave thumbs down.
     *
     * @param thumbsDownUsers the set of users who gave thumbs down
     */
    public void setThumbsDownUsers(Set<String> thumbsDownUsers) {
        this.thumbsDownUsers = thumbsDownUsers;
    }
}