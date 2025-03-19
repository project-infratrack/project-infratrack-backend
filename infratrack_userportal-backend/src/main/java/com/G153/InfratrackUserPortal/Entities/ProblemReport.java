package com.G153.InfratrackUserPortal.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a problem report in the system.
 */
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
    private String approval = "Pending";
    private Set<String> thumbsUpUsers = new HashSet<>();
    private Set<String> thumbsDownUsers = new HashSet<>();

    /**
     * Gets the ID.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID.
     *
     * @param id the ID
     */
    public void setId(String id) {
        this.id = id;
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
     * Sets the user ID.
     *
     * @param userId the user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the report type.
     *
     * @return the report type
     */
    public String getReportType() {
        return reportType;
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
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
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
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
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
     * Gets the image.
     *
     * @return the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the image.
     *
     * @param image the image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
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
     * Gets the longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
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
     * Gets the priority level.
     *
     * @return the priority level
     */
    public String getPriorityLevel() {
        return priorityLevel;
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
     * Gets the number of thumbs up.
     *
     * @return the number of thumbs up
     */
    public int getThumbsUp() {
        return thumbsUp;
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
     * Gets the number of thumbs down.
     *
     * @return the number of thumbs down
     */
    public int getThumbsDown() {
        return thumbsDown;
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
     * Gets the approval status.
     *
     * @return the approval status
     */
    public String getApproval() {
        return approval;
    }

    /**
     * Sets the approval status.
     *
     * @param approval the approval status
     */
    public void setApproval(String approval) {
        this.approval = approval;
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
     * Sets the set of users who gave thumbs up.
     *
     * @param thumbsUpUsers the set of users who gave thumbs up
     */
    public void setThumbsUpUsers(Set<String> thumbsUpUsers) {
        this.thumbsUpUsers = thumbsUpUsers;
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
     * Sets the set of users who gave thumbs down.
     *
     * @param thumbsDownUsers the set of users who gave thumbs down
     */
    public void setThumbsDownUsers(Set<String> thumbsDownUsers) {
        this.thumbsDownUsers = thumbsDownUsers;
    }
}