package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

public class OtpVerificationRequest {
    @NotBlank(message = "ID number is required")
    private String idNumber;

    @NotBlank(message = "OTP is required")
    private String otp;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

