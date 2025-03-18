package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for admin login requests.
 */
public class AdminLoginRequest {
    @NotBlank(message = "Admin No is required")
    private String adminNo;

    @NotBlank(message = "Password is required")
    private String password;

    /**
     * Gets the admin number.
     *
     * @return the admin number
     */
    public String getAdminNo() {
        return adminNo;
    }

    /**
     * Sets the admin number.
     *
     * @param adminNo the admin number
     */
    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
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