package com.G153.InfratrackUserPortal.DTO;

/**
 * Data Transfer Object for user report details.
 */
public class UserReportDetails {
    private String id;
    private String userId;
    private String reportType;
    private String description;
    private String location;
    private String image;
    private double latitude;
    private double longitude;
    private int thumbsUp;
    private int thumbsDown;

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
    public String getImage() {
        return image;
    }

    /**
     * Sets the image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
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
}