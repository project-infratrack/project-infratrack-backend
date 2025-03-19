package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for OTP verification requests.
 */
public class OtpVerificationRequest {
    @NotBlank(message = "ID number is required")
    private String idNumber;

    @NotBlank(message = "OTP is required")
    private String otp;

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
     * Gets the OTP.
     *
     * @return the OTP
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Sets the OTP.
     *
     * @param otp the OTP
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }
}