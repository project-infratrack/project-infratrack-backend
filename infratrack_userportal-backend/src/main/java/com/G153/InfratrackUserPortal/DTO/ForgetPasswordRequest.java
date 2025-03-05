package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

public class ForgetPasswordRequest {
    @NotBlank(message = "ID number is required")
    private String idNumber;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}