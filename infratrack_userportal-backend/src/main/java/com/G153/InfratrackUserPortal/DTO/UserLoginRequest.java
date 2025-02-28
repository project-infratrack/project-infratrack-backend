package com.G153.InfratrackUserPortal.DTO;

import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {
    @NotBlank(message = "ID number is required")
    private String idNumber;

    @NotBlank(message = "Password is required")
    private String password;

    // Getters and setters
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
