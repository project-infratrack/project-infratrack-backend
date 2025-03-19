package com.G153.InfratrackUserPortal.DTO;

/**
 * Data Transfer Object for user profile details.
 */
public class UserProfileDetails {
    private String name;
    private String idNumber;
    private String username;
    private String email;
    private String mobileNo;

    /**
     * Gets the ID number.
     *
     * @return the ID number
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Sets the ID number.
     *
     * @param idNumber the ID number
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the mobile number.
     *
     * @return the mobile number
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number.
     *
     * @param mobileNo the mobile number
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}