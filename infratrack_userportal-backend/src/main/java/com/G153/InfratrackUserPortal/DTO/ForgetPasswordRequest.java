package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for forget password requests.
 */
public class ForgetPasswordRequest {
    @NotBlank(message = "ID number is required")
    private String idNumber;

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
}