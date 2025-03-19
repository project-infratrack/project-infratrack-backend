package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user login requests.
 */
public class UserLoginRequest {
    @NotBlank(message = "ID number is required")
    private String idNumber;

    @NotBlank(message = "Password is required")
    private String password;

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
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}